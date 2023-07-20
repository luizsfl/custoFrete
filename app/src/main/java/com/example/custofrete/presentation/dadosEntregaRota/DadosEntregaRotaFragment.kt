package com.example.custofrete.presentation.dadosEntregaRota

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentDadosEntregaRotaBinding
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.Rota
import com.example.custofrete.presentation.ViewStateEntregaRota
import com.example.custofrete.presentation.ViewStateRota
import com.example.custofrete.presentation.adapter.EntregaRotaPendenteAdapter
import com.example.custofrete.presentation.listaEntregaRota.ListaEntregaRotaFragmentDirections
import com.example.custofrete.presentation.listaEntregaRota.ListaEntregaRotaViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DadosEntregaRotaFragment : Fragment() {

    private var _binding: FragmentDadosEntregaRotaBinding? = null
    private val binding get() = _binding!!
    private val args = navArgs<DadosEntregaRotaFragmentArgs>()
    private lateinit var entrega: Entrega
    private val viewModel: DadosEntregaRotaViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDadosEntregaRotaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        entrega = args.value.entrega

        if(entrega.listaRotas?.size!! > 0){

            setAdapterPendente(entrega.listaRotas!!)

        }

        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        binding.ivListaEntrega.setOnClickListener {
            val action =  DadosEntregaRotaFragmentDirections.actionDadosRotaFragmentToListaEntregaRotaFragment()
            findNavController().navigate(action)
        }

        binding.ivVoltar.setOnClickListener{
            val action =  DadosEntregaRotaFragmentDirections.actionDadosRotaFragmentToHomeFragment()
            findNavController().navigate(action)
        }

        binding.btComecar.setOnClickListener{
            val rota = entrega.listaRotas!!.get(1)
            selecionarAppMapa(requireContext(),rota)
        }

        viewModel.viewStateListEntregaRota.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewStateRota.sucesso -> setAdapterPendente(viewState.listRota)
                else -> {}
            }
        }

        (activity as AppCompatActivity).supportActionBar?.hide()

        return root
    }

    private fun setAdapterPendente(listEntregaRota: List<Rota>) {
        val listaRotaPendente = listEntregaRota.filter {
            it.status == "pendente"
        }

        val rotaAdapter = EntregaRotaPendenteAdapter(listaRotaPendente)
         rotaAdapter.onItemClickEntregue = { rota, listRota,posicao ->
             val listaUpdate = listEntregaRota.toMutableList()
             rota.status = "Entregue"
             listaUpdate.set(posicao,rota)
             viewModel.updateEntregaRota(entrega.idDocument,listaUpdate)
         }

        binding.recyclerview.adapter = rotaAdapter
    }


    private fun selecionarAppMapa(contextTela : Context,destino:Rota){

        val builder = AlertDialog.Builder(contextTela!!)
        var tipoMapa = 1
        val view: View
        val inflater = contextTela!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.layout_selecionar_app_mapa,null)

        val ggmaps = view.findViewById<LinearLayout>(R.id.ll_google_maps)
        val wazemaps = view.findViewById<LinearLayout>(R.id.ll_waze_maps)
        val outromaps = view.findViewById<LinearLayout>(R.id.ll_outro_maps)

        ggmaps.setOnClickListener {
            tipoMapa = 1
            ggmaps.setBackgroundColor(resources.getColor(R.color.selectedButonMaps))
            wazemaps.setBackgroundColor(resources.getColor(R.color.white))
            outromaps.setBackgroundColor(resources.getColor(R.color.white))
        }

        wazemaps.setOnClickListener {
            tipoMapa = 2
            ggmaps.setBackgroundColor(resources.getColor(R.color.white))
            wazemaps.setBackgroundColor(resources.getColor(R.color.selectedButonMaps))
            outromaps.setBackgroundColor(resources.getColor(R.color.white))
        }

        outromaps.setOnClickListener {
            tipoMapa = 3
            ggmaps.setBackgroundColor(resources.getColor(R.color.white))
            wazemaps.setBackgroundColor(resources.getColor(R.color.white))
            outromaps.setBackgroundColor(resources.getColor(R.color.selectedButonMaps))
        }

        builder.setView(view)
            .setTitle("Selecione um aplicativo de mapa")

        builder.setPositiveButton("OK") { dialog, which -> }

        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()

        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                when(tipoMapa){
                    1-> openGoogleMaps(destino.lat,destino.lng)
                    2-> openWaze(destino.lat,destino.lng)
                    3-> openOderMaps(destino.lat,destino.lng)
                }
                dialog.dismiss()
            }
        }


        dialog.show()

    }

    fun openGoogleMaps(latitude: Double, longitude: Double) {
        try {
            val gmmIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }catch (e:Exception){
            Toast.makeText(context, "Google maps Não foi encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWaze(latitude: Double, longitude: Double) {
        try {
            val url = "waze://?ll=$latitude,$longitude&navigate=yes"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)

        }catch (e:Exception){
            Toast.makeText(context, "Waze Não foi encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    fun openOderMaps(latitude: Double, longitude: Double) {
        try {
            val uri = "geo:$latitude,$longitude"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)

        }catch (e:Exception){
            Toast.makeText(context, "Waze Não foi encontrado", Toast.LENGTH_SHORT).show()
        }
    }
}
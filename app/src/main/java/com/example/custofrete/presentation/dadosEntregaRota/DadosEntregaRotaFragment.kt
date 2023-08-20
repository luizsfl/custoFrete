package com.example.custofrete.presentation.dadosEntregaRota

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentDadosEntregaRotaBinding
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.Rota
import com.example.custofrete.presentation.ViewStateRota
import com.example.custofrete.presentation.adapter.EntregaRotaPendenteAdapter
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
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


        if(entrega.tipoTela == 1){
            if(entrega.listaRotas?.size!! > 0){
                setAdapter(entrega.listaRotas!!,entrega.tipoTela)
                binding.txtTitulo.text = "Rota informada pendente"

            }
        }else if(entrega.tipoTela == 2){
            binding.txtTitulo.text = "Melhores rotas pendente"
            if(entrega.listaMelhorRota?.size!! > 0){
                setAdapter(entrega.listaMelhorRota!!,entrega.tipoTela)
            }
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewEntregue.layoutManager = LinearLayoutManager(context)

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
                is ViewStateRota.Loading -> showLoading(viewState.loading)
                is ViewStateRota.sucesso -> setAdapter(viewState.listRota,entrega.tipoTela)
                is ViewStateRota.Failure -> showErro(viewState.messengerError)
                else -> {}
            }
        }

        (activity as AppCompatActivity).supportActionBar?.hide()

        return root
    }

    private fun setAdapter(listEntregaRota: List<Rota>,tipoTela:Int) {

        setAdapterPendente(listEntregaRota,tipoTela)

        setAdapterEntregue(listEntregaRota,tipoTela)

        showLoading(false,false)
    }

    private fun setAdapterPendente(listEntregaRota: List<Rota>,tipoTela: Int): EntregaRotaPendenteAdapter {
        val listaRotaPendente = listEntregaRota.filter {
            it.status.toString().equals("pendente")
        }

        if (listaRotaPendente.isEmpty()){
            binding.txtTitulo.visibility = View.GONE
            binding.btComecar.visibility = View.GONE
        }else{
            binding.btComecar.visibility = View.VISIBLE
            binding.txtTitulo.visibility = View.VISIBLE
        }

        val rotaAdapter = EntregaRotaPendenteAdapter(listaRotaPendente)
        rotaAdapter.onItemClickEntregue = { rota, listRota, posicao ->
            showLoading(true)
            val listaUpdate = listEntregaRota.map { it.copy() }.toMutableList()
            rota.status = "entregue"
            listaUpdate.set(rota.posicao!!, rota)
            if (rota != null) {
                viewModel.updateEntregaRota(entrega.idDocument, listaUpdate, entrega.tipoTela)
            }
        }

        rotaAdapter.onItemClickNaoEntregue = { rota, listRota, posicao ->
            showLoading(true)
            val listaUpdate =  listEntregaRota.map { it.copy() }.toMutableList()
            rota.status = "NaoEntregue"
            listaUpdate.set(rota.posicao!!, rota)
            if(rota !=  null){
                viewModel.updateEntregaRota(entrega.idDocument, listaUpdate,entrega.tipoTela)
            }
        }

        binding.recyclerview.adapter = rotaAdapter
        return rotaAdapter
    }


    private fun setAdapterEntregue(listEntregaRota: List<Rota>,tipoTela: Int): EntregaRotaPendenteAdapter {
        val listaRotaFinalizada = listEntregaRota.filter {
            !it.status.toString().equals("pendente")
        }

        val rotaAdapter = EntregaRotaPendenteAdapter(listaRotaFinalizada)
//        rotaAdapter.onItemClickEntregue = { rota, listRota, posicao ->
//            val listaUpdate = listEntregaRota.toMutableList()
//            rota.status = "entregue"
//            listaUpdate.set(posicao, rota)
//            viewModel.updateEntregaRota(entrega.idDocument, listaUpdate)
//        }
//
//        rotaAdapter.onItemClickNaoEntregue = { rota, listRota, posicao ->
//            val listaUpdate = listEntregaRota.toMutableList()
//            rota.status = "NaoEntregue"
//            listaUpdate.set(posicao, rota)
//            viewModel.updateEntregaRota(entrega.idDocument, listaUpdate)
//        }

        binding.recyclerviewEntregue.adapter = rotaAdapter
        return rotaAdapter
    }

    private fun showLoading(isLoading: Boolean,isButon:Boolean = true) {
        binding.carregamento.isVisible = isLoading
        if(isButon){
            binding.btComecar.isVisible = !isLoading
        }
    }

    private fun showErro(text: String) {
        var view = binding.root.rootView
        val snackBarView = Snackbar.make(view, text , Snackbar.LENGTH_LONG)
        view = snackBarView.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.CENTER
        view.layoutParams = params
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()

        showLoading(false)
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
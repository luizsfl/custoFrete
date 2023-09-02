package com.example.custofrete.presentation.calculoRota

import android.app.AlertDialog
import android.content.Context
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custofrete.BuildConfig.GOOGLE_MAPS_KEY
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentCalculoBinding
import com.example.custofrete.domain.model.*
import com.example.custofrete.presentation.ViewStateCustoCalculado
import com.example.custofrete.presentation.ViewStateEntregaRota
import com.example.custofrete.presentation.adapter.RotaAdapter
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class CalculoRotaFragment : Fragment() {

    private var _binding: FragmentCalculoBinding? = null
    private val binding get() = _binding!!
    private val args = navArgs<CalculoRotaFragmentArgs>()
    private val viewModel: CalculoRotaViewModel by viewModel()
    private lateinit var entrega: Entrega
    private var menorRotaAdapter: MutableList<Rota> = mutableListOf()
    private var posicaoMelhorRota = 1
    private var kmMelhorRota = "0"
    private var custoKmInformado = 0.0
    private var rotaSelecionada = 1
    private var  valorInformadoCalculado :String = "0.00"
    private var  valorMelhorCalculado :String = "0.00"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        entrega = args.value.entrega

        entrega.listaRotas?.let { setHomeListAdapter(it) }

        custoKmInformado = distanciaRota(entrega.listaRotas,1)

        calculoMenorRota()

        binding.lnRotaInformada.setOnClickListener {
            rotaSelecionada = 1
            binding.lnRotaInformada.setBackgroundColor(getResources().getColor(R.color.botonSelected))
            binding.lnRotaCalculada.setBackgroundColor(getResources().getColor(R.color.white))

            entrega.listaRotas?.let { setHomeListAdapter(it) }
            distanciaRota(entrega.listaRotas,1)

        }

        binding.lnRotaCalculada.setOnClickListener {
            rotaSelecionada = 2
            binding.lnRotaCalculada.setBackgroundColor(getResources().getColor(R.color.botonSelected))
            binding.lnRotaInformada.setBackgroundColor(getResources().getColor(R.color.white))
            setHomeListAdapter(menorRotaAdapter)

            binding.tvValorTotalKm.text = "Essa rota percorre $kmMelhorRota km"

        }

        binding.btSalvar.setOnClickListener {
            entrega.tipoTela = rotaSelecionada
            showAlertDialog(requireContext())

        }

        (activity as AppCompatActivity).supportActionBar?.hide()

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        valorInformadoCalculado = calcularValorRota(custoKmInformado,entrega)

        binding.mtvValorInformado.text = "R$: ${valorInformadoCalculado}"

        viewModel.viewStateCustoRotaCalculada.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewStateCustoCalculado.Loading -> {
                    binding.mtvValorMelhorRota.text = "Calculando..."
                }
                is ViewStateCustoCalculado.Failure -> {
                    binding.mtvValorMelhorRota.text = "Erro no calculo, tente mais tarde."
                    showErro(viewState.messengerError)
                }
                is ViewStateCustoCalculado.sucessoCustoCalculado -> {
                    val kmCalculado = viewState.custoCalculado
                    val df = DecimalFormat("#.##")
                    val roundoff = df.format(kmCalculado)
                    kmMelhorRota = roundoff

                    valorMelhorCalculado = calcularValorRota(kmCalculado,entrega)

                    binding.mtvValorMelhorRota.text = "R$: ${valorMelhorCalculado}"

                }
                else -> {}
            }
        }

        viewModel.viewStateEntregaRota.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewStateEntregaRota.Loading -> {
                    showLoading(true)
                 }
                is ViewStateEntregaRota.sucesso -> {
                    showLoading(false)
                    val action =  CalculoRotaFragmentDirections.actionCalculoFragmentToDadosRotaFragment(entrega)
                    findNavController().navigate(action)
                }
                is ViewStateEntregaRota.Failure ->{
                    showErro(viewState.messengerError)
                }
                else -> {}
            }
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

       // showLoading(false)
    }

    private fun calcularValorRota(calcularValorRota: Double,entrega: Entrega):String{
        var valorCalculado: String
        val valorMediaGasolina = entrega.custoViagem?.valorGasolina
        val valorMediaAlimentacao = entrega.custoViagem?.valorAlimentacao!!
        val valorMediaHotel = entrega.custoViagem?.valorHotel!!
        val valorMediaGastos  = entrega.custoViagem?.gastosExtras!!

        val valorKmMedia = entrega.dadosVeiculo.qtdKmLitro
        var valorPorLitro = 0.0

        if(valorMediaGasolina != null && valorMediaGasolina != 0.0){
            valorPorLitro = valorKmMedia / valorMediaGasolina
        }

        var calcTotal = calcularValorRota * valorPorLitro
        calcTotal += valorMediaAlimentacao + valorMediaHotel + valorMediaGastos

        val df = DecimalFormat("#.##")
        valorCalculado = df.format(calcTotal)

        if(valorCalculado.equals("0")){
            valorCalculado = "0.00"
        }

        return valorCalculado

    }

    private fun calculoMenorRota() {
        if (entrega.listaRotas?.size!! > 0) {

            if(entrega.listaRotas?.size!! > 1){
                viewModel.setLoadingDistanciaRotaCalculada(true)
            }

            val points = mutableListOf<Point>()
            entrega.listaRotas?.forEachIndexed { index, rota ->
                points.add(Point(rota.lat, rota.lng, index))
            }

            val menorRota = nearestNeighborAlgorithm(points)
            menorRotaAdapter = mutableListOf<Rota>()

            menorRota.forEach {
                val rota = entrega.listaRotas!![it.posicao].copy()
                rota.valorDistance = Distance()

                menorRotaAdapter.add(rota)
            }


            for (i in 0..menorRotaAdapter.size) {
                if (i > 1) {
                    val tste = menorRotaAdapter
                    val location1 = LatLng(menorRotaAdapter.get(i - 2).lat,menorRotaAdapter.get(i - 2).lng)
                    val location2 = LatLng(menorRotaAdapter.get(i - 1).lat,menorRotaAdapter.get(i - 1).lng)

                    var start = Location("Start Point");
                    start.setLatitude(location1.latitude);
                    start.setLongitude(location1.longitude);

                    var finish = Location("Finish Point");

                    finish.setLatitude(location2.latitude);
                    finish.setLongitude(location2.longitude);

                    // Log.d("GoogleMap", "before URL")
                    var URL = getDirectionURL(location1, location2)
                    // Log.d("GoogleMap", "URL : $URL")
                    GetDirection(URL).execute()
                }
            }
        }
    }

    private fun distanciaRota(listRota: List<Rota>?,tipoCalculo:Int):Double {
        var valorMetroSequencia = 0.0
        if(listRota?.size ?:0 > 1){
            listRota?.forEach {
                valorMetroSequencia += it.valorDistance.value
            }
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        val random = valorMetroSequencia / 1000

        val df = DecimalFormat("#.#")
        val roundoff = df.format(random)

        if(tipoCalculo == 1)
        {
            //binding.tvValorTotalKm.text = "Essa rota percorre $roundoff km"
            binding.tvValorTotalKm.text = "Essa rota percorre ${roundoff} km"

        }else if (tipoCalculo == 2){
            kmMelhorRota = roundoff
        }

        return random
    }

    private fun showAlertDialog(contextTela: Context) {

        val builder = androidx.appcompat.app.AlertDialog.Builder(contextTela!!)

        val view: View
        val inflater = contextTela!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.total_custo_calc_rota,null)

        val tiTitulo = view.findViewById<TextInputEditText>(R.id.ti_titulo_rota)
        val tiValorCobrado = view.findViewById<TextInputEditText>(R.id.ti_valor_cobrado_rota)

        tiValorCobrado.setText(entrega.valorEntrega.toString())

        if(entrega.titulo.isEmpty()){
            tiTitulo.setHint("Digite aqui")
        }else{
            tiTitulo.setText(entrega.titulo)
        }

        tiTitulo.inputType = InputType.TYPE_CLASS_TEXT

        builder.setView(view)
            .setTitle("")

        builder.setPositiveButton("OK") { dialog, which -> }

        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()

        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {

                var titulo = tiTitulo.text.toString()
                var valorEntrega = tiValorCobrado.text.toString()

                if(titulo.isEmpty()){
                    val erro = "Informe um título para identificar essa entrega"
                    tiTitulo.error = erro
                }else{

                    entrega.titulo = titulo
                    entrega.valorEntrega =  if (valorEntrega.isEmpty()) 0.0 else valorEntrega.toDouble()
                    entrega.totalKm =  if (kmMelhorRota.isEmpty()) 0.0 else kmMelhorRota.replace(",",".").toDouble()
                    entrega.listaMelhorRota = menorRotaAdapter
                    entrega.valorInformadoCalculado = if (valorInformadoCalculado.isEmpty()) 0.0 else valorInformadoCalculado.replace(",",".").toDouble()
                    entrega.valorMelhorCalculado = if (valorMelhorCalculado.isEmpty()) 0.0 else valorMelhorCalculado.replace(",",".").toDouble()

                    viewModel.addEntregaRota(entrega)


                    dialog.dismiss()

                }
            }
        }

        dialog.show()
    }

    private fun setHomeListAdapter(listRota: List<Rota>) {
        val rotaAdapter = RotaAdapter(listRota)
        rotaAdapter.onItemClick = {
//            val intent = Intent(requireContext(), HomeDetailActivity::class.java)
//                .apply {
//                    putExtra("idCaixa", it.idCaixa)
//                }
//            startActivity(intent)
        }

        binding.recyclerview.adapter = rotaAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.carregamento.isVisible = isLoading
        binding.btSalvar.isVisible = !isLoading
    }

    fun nearestNeighborAlgorithm(points: List<Point>): List<Point> {
        val visitedPoints = mutableListOf<Point>()
        var currentPoint = points.first()
        visitedPoints.add(currentPoint)
        while (visitedPoints.size < points.size) {
            val nextPoint = points.filter { !visitedPoints.contains(it) }
                .minByOrNull { it.distanceTo(currentPoint) }
            nextPoint?.let {
                visitedPoints.add(it)
                currentPoint = it
            }
        }
        return visitedPoints
    }

    data class Point(val latitude: Double, val longitude: Double, val posicao: Int) {
        fun distanceTo(other: Point): Double {
            val earthRadius = 6371 // km
            val dLat = Math.toRadians(other.latitude - latitude)
            val dLon = Math.toRadians(other.longitude - longitude)
            val lat1 = Math.toRadians(latitude)
            val lat2 = Math.toRadians(other.latitude)
            val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2)
            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
            return earthRadius * c
        }
    }

    private inner class GetDirection(val url: String) :
        AsyncTask<Void, Void, List<List<LatLng>>>() {
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {

            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body()!!.string()
            Log.d("GoogleMap", " data : $data")
            val result = ArrayList<List<LatLng>>()


            try {
                val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)

                val path = ArrayList<LatLng>()
                menorRotaAdapter.get(posicaoMelhorRota - 1).valorDistance =
                    respObj.routes.get(0).legs.get(0).distance

                posicaoMelhorRota++

                result.add(path)

            } catch (e: Exception) {
                e.printStackTrace()
                viewModel.setErroDistanciaRotaCalculada(e.message.toString())
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            if(posicaoMelhorRota == menorRotaAdapter.size ){
                viewModel.getDistanciaRotaCalculada(menorRotaAdapter)
            }
        }

    }

    fun getDirectionURL(origin: LatLng, dest: LatLng): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&sensor=false&key=${GOOGLE_MAPS_KEY}"
    }

}
package com.example.custofrete.presentation.calculo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentCalculoBinding
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.Rota
import com.example.custofrete.presentation.adapter.RotaAdapter
import java.text.DecimalFormat

class CalculoFragment : Fragment() {

    private var _binding: FragmentCalculoBinding? = null
    private val binding get() = _binding!!
    private val args = navArgs<CalculoFragmentArgs>()
    private lateinit var entrega: Entrega
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        entrega = args.value.entrega

        entrega.listaRotas?.let { setHomeListAdapter(it) }

        distanciaRota(entrega.listaRotas)

        binding.lnRotaInformada.setOnClickListener {
            binding.lnRotaInformada.setBackgroundColor(getResources().getColor(R.color.botonSelected))
            binding.lnRotaCalculada.setBackgroundColor(getResources().getColor(R.color.white))

            entrega.listaRotas?.let { setHomeListAdapter(it) }

        }

        binding.lnRotaCalculada.setOnClickListener{
            binding.lnRotaCalculada.setBackgroundColor(getResources().getColor(R.color.botonSelected))
            binding.lnRotaInformada.setBackgroundColor(getResources().getColor(R.color.white))

            if(entrega.listaRotas?.size!! > 0) {
                val points = mutableListOf<Point>()
                entrega.listaRotas?.forEachIndexed { index, rota ->
                    points.add(Point(rota.latLng.latitude, rota.latLng.longitude, index))
                }

                val menorRota = nearestNeighborAlgorithm(points)
                var menorRotaAdapter = mutableListOf<Rota>()

                menorRota.forEach {
                    menorRotaAdapter.add(entrega.listaRotas!![it.posicao])
                }

                setHomeListAdapter(menorRotaAdapter)
                distanciaRota(menorRotaAdapter)

                //            val points = listOf(
//                Point(51.5074, -0.1278),
//                Point(-33.8688, 151.2093),
//                Point(37.7749, -122.4194),
//                Point(40.7128, -74.0060),
//                Point(48.8566, 2.3522)
//            )

                println("vizinho mais proximo" + nearestNeighborAlgorithm(points))
            }


        }

        (activity as AppCompatActivity).supportActionBar?.hide()

        return root

    }

    private fun distanciaRota(listRota: List<Rota>?) {
        var valorMetroSequencia = 0.0
        listRota?.forEach {
            valorMetroSequencia += it.valorDistance.value
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        val random = valorMetroSequencia / 1000

        val df = DecimalFormat("#.#")
        val roundoff = df.format(random)

        binding.tvValorTotalKm.text = "Essa rota percorre $roundoff km"
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

    data class Point(val latitude: Double, val longitude: Double,val posicao:Int) {
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
}
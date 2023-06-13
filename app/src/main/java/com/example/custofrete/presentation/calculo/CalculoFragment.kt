package com.example.custofrete.presentation.calculo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.custofrete.databinding.FragmentCalculoBinding

class CalculoFragment : Fragment() {

    private var _binding: FragmentCalculoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val graph = arrayOf(
           intArrayOf(0, 10, 15, 20), //10  1
           intArrayOf(10, 0, 35, 25), // 25 0
           intArrayOf(15, 35, 0, 30), //    3
           intArrayOf(1, 25, 30, 0)  //    2
        )

        getMelhorRota(graph)

        (activity as AppCompatActivity).supportActionBar?.hide()

        return root

    }

    fun getMelhorRota(graph :Array<IntArray>) {
        val tsp = TSP(graph)
        println(tsp.getTour())
    }

    class TSP(private val graph: Array<IntArray>) {
        private val n = graph.size
        private val visited = BooleanArray(n)
        private var minTourCost  = Int.MAX_VALUE
        private var bestTourOrder: List<Int>? = null

        fun getTour(): String {
            tsp(0, mutableListOf(), 0)
            return "A ordem ótima da turnê é $bestTourOrder e o custo mínimo é $minTourCost"
        }



        private fun tsp(currentVertex: Int, tourOrder: MutableList<Int>, currentCost: Int) {
            if (tourOrder.size == n && graph[currentVertex][0] != 0) {
                if (currentCost + graph[currentVertex][0] < minTourCost) {
                    bestTourOrder = tourOrder.toList()
                    minTourCost = currentCost + graph[currentVertex][0]
                }
                return
            }
            for (i in 0 until n) {
                if (!visited[i] && graph[currentVertex][i] != 0) {
                    visited[i] = true
                    tourOrder.add(i)
                    tsp(i, tourOrder, currentCost + graph[currentVertex][i])
                    visited[i] = false
                    tourOrder.removeAt(tourOrder.size - 1)
                }
            }
        }
    }
}
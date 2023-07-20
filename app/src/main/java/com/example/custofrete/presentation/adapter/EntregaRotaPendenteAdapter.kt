package com.example.custofrete.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.custofrete.databinding.ItemDadosEntregaRotaBinding
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.Rota

class EntregaRotaPendenteAdapter(private val dataSet: List<Rota>) :
    RecyclerView.Adapter<EntregaRotaPendenteAdapter.ViewHolder>() {

    var onItemClickEntregue : ((Rota,List<Rota>,Int)-> Unit)? = null
    var onItemClickNaoEntregue : ((Rota,List<Rota>,Int)-> Unit)? = null

    class ViewHolder(val binding: ItemDadosEntregaRotaBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Rota){

            binding.titleRota.text = item.title

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemFilterAdapter = ItemDadosEntregaRotaBinding.inflate(inflater,viewGroup,false)

        return ViewHolder(itemFilterAdapter,viewGroup.context)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])

        viewHolder.binding.llEntregaOk.setOnClickListener {
            onItemClickEntregue?.invoke(dataSet[position],dataSet,position)
        }

        viewHolder.binding.llEntregaNao.setOnClickListener {
            onItemClickNaoEntregue?.invoke(dataSet[position],dataSet,position)
        }

    }

    override fun getItemCount() = dataSet.size

}
package com.example.custofrete.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.custofrete.databinding.ItemEntregaRotaBinding
import com.example.custofrete.domain.model.EntregaSimples

class EntregaSimplesAdapter(private val dataSet: List<EntregaSimples>) :
    RecyclerView.Adapter<EntregaSimplesAdapter.ViewHolder>() {

    var onItemClick : ((EntregaSimples)-> Unit)? = null
    var onItemClickExcluir : ((EntregaSimples)-> Unit)? = null
    var onItemClickEditar : ((EntregaSimples)-> Unit)? = null
    var onItemClickVisualizar : ((EntregaSimples)-> Unit)? = null

    class ViewHolder(val binding: ItemEntregaRotaBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EntregaSimples){

            binding.titleRota.text = item.titulo

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemFilterAdapter = ItemEntregaRotaBinding.inflate(inflater,viewGroup,false)

        return ViewHolder(itemFilterAdapter,viewGroup.context)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])

        viewHolder.binding.lcDados.setOnClickListener {
            onItemClick?.invoke(dataSet[position])
        }
        viewHolder.binding.ivVisualizar.setOnClickListener {
            onItemClickVisualizar?.invoke(dataSet[position])
        }

        viewHolder.binding.ivExcluir.setOnClickListener {
            onItemClickExcluir?.invoke(dataSet[position])
        }

        viewHolder.binding.ivEditar.setOnClickListener {
            onItemClickEditar?.invoke(dataSet[position])
        }

    }

    override fun getItemCount() = dataSet.size

}
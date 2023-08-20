package com.example.custofrete.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.custofrete.databinding.ItemEntregaRotaBinding
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.Rota

class EntregaRotaAdapter(private val dataSet: List<Entrega>) :
    RecyclerView.Adapter<EntregaRotaAdapter.ViewHolder>() {

    var onItemClick : ((Entrega)-> Unit)? = null
    var onItemClickExcluir : ((Entrega)-> Unit)? = null
    var onItemClickEditar : ((Entrega)-> Unit)? = null
    var onItemClickVisualizar : ((Entrega)-> Unit)? = null

    class ViewHolder(val binding: ItemEntregaRotaBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Entrega){

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
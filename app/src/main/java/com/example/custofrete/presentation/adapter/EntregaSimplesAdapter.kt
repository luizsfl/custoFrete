package com.example.custofrete.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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

            binding.tiTipoRota.isVisible = false
            binding.titleRota.text = "Titulo: ${item.titulo}"
            binding.tiValorEntrega.text = "Valor cobrado R$: ${item.valorEntrega}"
            binding.tiValorCalculado.text = "Custo calculado R$: ${item.valorEntregaCalculado}"
            binding.tiQtdKilometragem.text = "Km: ${item.totalKm}"

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
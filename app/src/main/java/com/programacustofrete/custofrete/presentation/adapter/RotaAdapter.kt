package com.programacustofrete.custofrete.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.programacustofrete.custofrete.databinding.ItemRotaBinding
import com.programacustofrete.custofrete.domain.model.Rota

class RotaAdapter(private val dataSet: List<Rota>,private val exclui:Boolean = false) :
    RecyclerView.Adapter<RotaAdapter.ViewHolder>() {

    var onItemClick : ((Rota)-> Unit)? = null
    var onItemClickExcluir : ((Int)-> Unit)? = null

    class ViewHolder(val binding: ItemRotaBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Rota,lexclui:Boolean){
            binding.titleRota.text = item.title
            binding.llExcluir.isVisible = lexclui
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemFilterAdapter = ItemRotaBinding.inflate(inflater,viewGroup,false)

        return ViewHolder(itemFilterAdapter,viewGroup.context)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position],exclui)

        viewHolder.binding.llExcluir.setOnClickListener {
            onItemClickExcluir?.invoke(position)
        }

    }

    override fun getItemCount() = dataSet.size

}
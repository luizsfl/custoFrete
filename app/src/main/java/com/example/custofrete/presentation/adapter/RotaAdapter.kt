package com.example.custofrete.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.custofrete.databinding.ItemRotaBinding
import com.example.custofrete.domain.model.Rota

class RotaAdapter(private val dataSet: List<Rota>) :
    RecyclerView.Adapter<RotaAdapter.ViewHolder>() {

    var onItemClick : ((Rota)-> Unit)? = null

    class ViewHolder(val binding: ItemRotaBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Rota){

            binding.titleRota.text = item.title


        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemFilterAdapter = ItemRotaBinding.inflate(inflater,viewGroup,false)

        return ViewHolder(itemFilterAdapter,viewGroup.context)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])

        viewHolder.itemView.setOnClickListener {
            onItemClick?.invoke(dataSet[position])
        }
    }

    override fun getItemCount() = dataSet.size

}
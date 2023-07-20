package com.example.custofrete.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.custofrete.R
import com.example.custofrete.databinding.ItemDadosEntregaRotaBinding
import com.example.custofrete.domain.model.Rota

class EntregaRotaPendenteAdapter(private val dataSet: List<Rota>) :
    RecyclerView.Adapter<EntregaRotaPendenteAdapter.ViewHolder>() {

    var onItemClickEntregue : ((Rota,List<Rota>,Int)-> Unit)? = null
    var onItemClickNaoEntregue : ((Rota,List<Rota>,Int)-> Unit)? = null

    class ViewHolder(val binding: ItemDadosEntregaRotaBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Rota){

            if  (!item.status.toString().equals("pendente")){
                binding.llEntregaOk.visibility = View.GONE
                binding.llEntregaNao.visibility = View.GONE
                binding.llEntregaFinalizada.visibility = View.VISIBLE
                if  (item.status.toString().equals("entregue")){
                    val  drawable  = getDrawable(context.resources, R.drawable.ic_entregue_ok_24,null)
                    binding.ivEntregaFinalizada.setImageDrawable(drawable)
                    binding.txtEntregaFinalizada.text = "Entregue"
                }else  if(item.status.toString().equals("NaoEntregue")){
                    val  drawable  = getDrawable(context.resources, R.drawable.ic_entregue_nao_24,null)
                    binding.ivEntregaFinalizada.setImageDrawable(drawable)
                    binding.txtEntregaFinalizada.text = "Não entregue"
                }
            }

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
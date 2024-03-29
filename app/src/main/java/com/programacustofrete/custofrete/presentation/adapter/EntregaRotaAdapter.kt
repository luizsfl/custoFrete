package com.programacustofrete.custofrete.presentation.adapter

import android.content.Context
import android.graphics.Color.parseColor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.programacustofrete.custofrete.databinding.ItemEntregaRotaBinding
import com.programacustofrete.custofrete.domain.model.Entrega

class EntregaRotaAdapter(private val dataSet: List<Entrega>) :
    RecyclerView.Adapter<EntregaRotaAdapter.ViewHolder>() {

    var onItemClick : ((Entrega)-> Unit)? = null
    var onItemClickExcluir : ((Entrega)-> Unit)? = null
    var onItemClickEditar : ((Entrega)-> Unit)? = null
    var onItemClickVisualizar : ((Entrega)-> Unit)? = null

    class ViewHolder(val binding: ItemEntregaRotaBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Entrega){
            binding.titleRota.text = "Titulo: ${item.titulo}"
            binding.tiTipoRota.text = "Tipo de rota: ${if(item.tipoTela == 1) "Informada" else "Calculada"}"
            binding.tiValorEntrega.text = "Valor cobrado R$: ${item.valorEntrega}"
            binding.tiValorCalculado.text = "Custo calculado R$: ${if(item.tipoTela == 1) item.valorInformadoCalculado else item.valorMelhorCalculado}"
            binding.tiQtdKilometragem.text = "Km: ${item.totalKm}"
            binding.tiStatusEntrega.text = "Status: ${item.statusEntrega}"

            if(item.statusEntrega.equals("Concluída")){
                binding.tiStatusEntrega.setTextColor(parseColor("#FF03DAC5"))
            }else{
                binding.tiStatusEntrega.setTextColor(parseColor("#DA0303"))
            }
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
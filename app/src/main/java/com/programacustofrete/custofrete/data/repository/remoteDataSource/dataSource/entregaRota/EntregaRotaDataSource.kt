package com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.entregaRota

import com.programacustofrete.custofrete.domain.model.Entrega
import com.programacustofrete.custofrete.domain.model.Rota
import kotlinx.coroutines.flow.Flow


interface EntregaRotaDataSource {
    fun addEntregaRota(entrega: Entrega): Flow<Entrega>
    fun getAllEntregaRota(): Flow<List<Entrega>>
    fun deleteEntregaRota(entrega: Entrega): Flow<Entrega>
    fun updateEntregaRota(idDocument:String,listaRotas:List<Rota>,tipoTela:Int): Flow<List<Rota>>
    fun updateEntregaRotaStatus(idDocument:String,status:String): Flow<String>
}
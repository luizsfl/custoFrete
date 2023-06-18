package com.example.custofrete.domain.repository

import com.example.custofrete.domain.model.DadosVeiculo
import kotlinx.coroutines.flow.Flow

interface DadosVeiculoRepository {
    fun addDadosVeiculo(dadosVeiculo: DadosVeiculo): Flow<DadosVeiculo>
}
package com.example.custofrete.domain.model

data class Entrega(
    val dadosVeiculo: DadosVeiculo,
    val custoViagem: CustoViagem?,
    val listaRotas : List<Rota>?
):java.io.Serializable
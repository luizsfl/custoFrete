package com.example.custofrete.domain.model

data class Entrega(
    val dadosVeiculo: DadosVeiculo,
    val custoViagem: CustoViagem?,
    val listaRotas : List<Rota>?,
    var idUsuario: String = "",
    var tipoTela: Int = 0
    ):java.io.Serializable
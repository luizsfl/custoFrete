package com.example.custofrete.domain.model


data class Entrega constructor(
    val dadosVeiculo: DadosVeiculo,
    val custoViagem: CustoViagem? = null,
    val listaRotas : List<Rota>? = null,
    var idUsuario: String = "",
    var tipoTela: Int = 0,
    var idDocument: String = "",
    var titulo: String = "",
    var listaMelhorRota : List<Rota>? = null
    ):java.io.Serializable {
    constructor() : this(DadosVeiculo())
}
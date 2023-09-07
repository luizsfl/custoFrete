package com.programacustofrete.custofrete.domain.model


data class Entrega constructor(
    var dadosVeiculo: DadosVeiculo,
    var custoViagem: CustoViagem? = null,
    var listaRotas : List<Rota>? = null,
    var idUsuario: String = "",
    var tipoTela: Int = 0,
    var idDocument: String = "",
    var titulo: String = "",
    var listaMelhorRota : List<Rota>? = null,
    var valorEntrega :Double = 0.0,
    var valorInformadoCalculado :Double = 0.0,
    var valorMelhorCalculado :Double = 0.0,
    var totalKm:Double = 0.0,
    var statusEntrega: String = "Pendente",
    ):java.io.Serializable {
    constructor() : this(DadosVeiculo())
}
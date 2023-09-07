package com.programacustofrete.custofrete.domain.model

data class DadosVeiculo(
    val placaVeiculo:String,
    val qtdKmLitro:Double,
    val qtdEixo:Double,
    val pesoVeiculo:Double,
    val idUsuario:String
):java.io.Serializable
{
    constructor():this("",0.0,0.0,0.0,"")
}

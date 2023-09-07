package com.programacustofrete.custofrete.domain.model

data class CustoViagem(
    val valorAlimentacao:Double,
    val valorHotel:Double,
    val valorGasolina:Double,
    val gastosExtras:Double
):java.io.Serializable
{
    constructor():this(0.0,0.0,0.0,0.0)

}
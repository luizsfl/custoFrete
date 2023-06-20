package com.example.custofrete.domain.model

import android.os.Parcelable

data class DadosVeiculo(
    val placaVeiculo:String,
    val qtdKmLitro:Double,
    val qtdEixo:Double,
    val pesoVeiculo:Double,
    val idUsuario:String
)

package com.example.custofrete.domain.model

import android.os.Parcelable

data class DadosVeiculo(
    val placaVeiculo:String,
    val qtdKmLitro:Float,
    val qtdEixo:Int,
    val pesoVeiculo:Float,
    val idUsuario:String
)

package com.example.custofrete.domain.model

data class Rota(
    val title:String,
    val address:String,
    val Address_line:String,
    val phone:String,
    val pin_code:String,
    val feature:String,
    val more:String,
    val latLng: com.google.android.gms.maps.model.LatLng,
    var valorDistance: Distance = Distance()
)

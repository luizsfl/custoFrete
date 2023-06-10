package com.example.custofrete.domain.model

import com.example.custofrete.presentation.config.ConfiguracaoFirebase
import com.google.firebase.database.DatabaseReference


class Usuario {
    lateinit var idUsuario: String
    lateinit var nome: String
    lateinit var email: String
    lateinit var senha: String


    constructor(nome: String, email: String,senha : String, idUsuario :String) {
        this.nome = nome
        this.email = email
        this.senha = senha
        this.idUsuario = idUsuario
    }

    constructor() {
    }
    fun salvar() {
        var firebase: DatabaseReference = ConfiguracaoFirebase.getFirebaseDataBse()

        firebase.child("usuarios")
            .child(this.idUsuario)
            .setValue(this)
    }
}
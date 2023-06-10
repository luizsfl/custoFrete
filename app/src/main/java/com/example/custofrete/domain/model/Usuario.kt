package com.example.custofrete.domain.model

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Usuario {
 var idUsuario: String
 var nome: String
 var email: String
 var senha: String


    constructor(nome: String, email: String,senha : String, idUsuario :String) {
        this.nome = nome
        this.email = email
        this.senha = senha
        this.idUsuario = idUsuario
    }
    fun salvar(){

        val db = Firebase.firestore

        db.collection("usuarios")
            .document(this.idUsuario)
            .set(this)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }
}
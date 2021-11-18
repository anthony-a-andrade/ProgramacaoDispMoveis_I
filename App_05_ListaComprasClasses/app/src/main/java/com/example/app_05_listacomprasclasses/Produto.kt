package com.example.app_05_listacomprasclasses

import android.graphics.Bitmap

data class Produto (
    val nome: String,
    val qnt: Int,
    val preco: Double,
    val foto: Bitmap? = null
)
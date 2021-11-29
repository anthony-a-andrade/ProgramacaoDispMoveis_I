package com.anthony.projeto_controlefinanceiro

data class Movimentacao(
    val id: Int,
    val nome: String,
    val valor: Double,
    val data: String,
    val mes: Int,
    val ano: Int,
    val tipo: Int)
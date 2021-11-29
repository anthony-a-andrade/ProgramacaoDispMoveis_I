package com.anthony.projeto_controlefinanceiro

import android.content.Context
import java.text.NumberFormat
import java.util.*

val Context.database: MovimentacoesDatabase
    get() = MovimentacoesDatabase.getInstance(applicationContext)

val Context.controller: MovimentacoesController
    get() = MovimentacoesController(applicationContext.database)

val Context.formatter: NumberFormat
    get() = NumberFormat.getCurrencyInstance(Locale("pt", "br"))

var movId: Int = -1
package com.anthony.projeto_controlefinanceiro

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.bloco_home_entradas.*
import kotlinx.android.synthetic.main.bloco_home_filtro.*
import kotlinx.android.synthetic.main.bloco_home_saidas.*
import kotlinx.android.synthetic.main.bloco_home_saldo.*
import kotlinx.android.synthetic.main.bloco_menu.*
import kotlinx.android.synthetic.main.bloco_menu_add_mov.*
import kotlinx.android.synthetic.main.bloco_menu_home.*
import kotlinx.android.synthetic.main.bloco_menu_listar_mov.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomeActivity : AppCompatActivity() {
    private lateinit var dateDialog: MonthYearPickerDialog
    private var filtrar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Menu(this, pnlHome, pnlAddMovimentacao, pnlListarMovimentacao, btnExpandir, pnlHome).enable()

        val aplicarData = HomeActivity::class.java.getMethod("aplicarData")
        dateDialog = MonthYearPickerDialog(this, aplicarData)

        lblData.setOnClickListener { dateDialog.showDialog() }
        btnLimpar.setOnClickListener { removerFiltro() }

        carregarDados()
    }

    fun removerFiltro() {
        filtrar = false
        lblData.text = "MMM/YY"

        carregarDados()
    }

    fun aplicarData() {
        val data = dateDialog.getResult()
        filtrar = true

        lblFiltro.text = "[$data]"
        lblData.text = data

        carregarDados()
    }


    fun carregarDados() {
        val state = if (filtrar) View.VISIBLE else View.GONE
        btnLimpar.visibility = state
        lblFiltro.visibility = state

        doAsync {
            val movs =
                when (filtrar) {
                    true -> controller.findAllByDate(dateDialog.getResult())
                    false -> controller.findAll()
                }!!

            val entradas = movs.filter { it.tipo == 0 }.sumByDouble { it.valor }
            val saidas = movs.filter { it.tipo == 1 }.sumByDouble { it.valor }
            val saldo = entradas - saidas

            uiThread {
                lblEntradas.text = formatter.format(entradas)
                lblSaidas.text = formatter.format(saidas)
                lblSaldo.text = formatter.format(saldo)
                lblSaldo.setTextColor(
                    when(true)
                    {
                        saldo > 0 -> Color.parseColor("#0B3E00")
                        saldo < 0 -> Color.parseColor("#6C0000")
                        else -> Color.parseColor("#000000")
                    }
                )
            }
        }
    }
}
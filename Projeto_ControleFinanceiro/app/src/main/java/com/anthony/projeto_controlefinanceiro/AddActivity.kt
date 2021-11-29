package com.anthony.projeto_controlefinanceiro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.bloco_add_data_tipo.*
import kotlinx.android.synthetic.main.bloco_add_item.*
import kotlinx.android.synthetic.main.bloco_add_valor.*
import kotlinx.android.synthetic.main.bloco_menu.*
import kotlinx.android.synthetic.main.bloco_menu_add_mov.*
import kotlinx.android.synthetic.main.bloco_menu_home.*
import kotlinx.android.synthetic.main.bloco_menu_listar_mov.*
import org.jetbrains.anko.toast

class AddActivity : AppCompatActivity() {
    private lateinit var dateDialog: MonthYearPickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        Menu(this, pnlHome, pnlAddMovimentacao, pnlListarMovimentacao, btnExpandir, pnlAddMovimentacao).enable()

        val aplicarData = AddActivity::class.java.getMethod("aplicarData")
        dateDialog = MonthYearPickerDialog(this, aplicarData)

        lblData.setOnClickListener { dateDialog.showDialog() }
        btnSalvar.setOnClickListener {
            val nome = txtItem.text.toString()
            val valor = txtValor.text.toString().toDoubleOrNull()
            val data = lblData.text.toString()
            val tipo = cboTipo.selectedItemPosition - 1

            when {
                nome.isEmpty() -> toast("Dê um nome para a movimentação!")
                valor == null -> toast("De um valor válido para a movimentação!")
                data == "MMM/YY" -> toast("Selecione uma data!")
                tipo == -1 -> toast("Selecione um tipo!")
                else -> toast(controller.save(nome, valor, dateDialog.getMesNum(), dateDialog.getAno(), data, tipo))
            }
        }
    }

    fun aplicarData() {
        lblData.text = dateDialog.getResult()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        pnlHome.performClick()
    }
}
package com.anthony.projeto_controlefinanceiro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.bloco_add_data_tipo.*
import kotlinx.android.synthetic.main.bloco_add_item.*
import kotlinx.android.synthetic.main.bloco_add_valor.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class EditActivity : AppCompatActivity() {
    private lateinit var dateDialog: MonthYearPickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val mov = controller.findById(movId)!!
        val aplicarData = EditActivity::class.java.getMethod("aplicarData")
        dateDialog = MonthYearPickerDialog(this, aplicarData)

        txtItem.setText(mov.nome)
        txtValor.setText(mov.valor.toString())
        cboTipo.setSelection(mov.tipo + 1)
        dateDialog.definirData(mov.mes, mov.ano)
        this.aplicarData()

        lblData.setOnClickListener { dateDialog.showDialog() }
        btnVoltar.setOnClickListener { startActivity<ListActivity>() }
        btnSalvar.setOnClickListener {
            val nome = txtItem.text.toString()
            val valor = txtValor.text.toString().toDoubleOrNull()
            val data = lblData.text.toString()
            val tipo = cboTipo.selectedItemPosition - 1

            when {
                nome.isEmpty() -> toast("Dê um nome para a movimentação!")
                valor == null -> toast("De um valor válido para a movimentação!")
                tipo == -1 -> toast("Selecione um tipo!")
                else -> {
                    controller.update(movId, nome, valor, dateDialog.getMesNum(), dateDialog.getAno(), data, tipo)
                    toast("Movimentação atualizada!")
                    onBackPressed()
                }
            }
        }
    }

    fun aplicarData() {
        lblData.text = dateDialog.getResult()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity<ListActivity>()
    }
}
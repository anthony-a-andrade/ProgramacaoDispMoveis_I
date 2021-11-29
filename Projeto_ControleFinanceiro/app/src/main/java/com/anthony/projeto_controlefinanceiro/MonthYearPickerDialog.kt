package com.anthony.projeto_controlefinanceiro

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import java.lang.reflect.Method

class MonthYearPickerDialog {
    private var ok: Button
    private var mes: Spinner
    private var ano: Spinner
    private var dialog: Dialog

    constructor(activity: Activity, method: Method) {
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_month_year_picker)

        val adapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, YearGenerator.generate(2000, 2099))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        ok = dialog.findViewById(R.id.btnOK)
        mes = dialog.findViewById(R.id.cboMes)
        ano = dialog.findViewById(R.id.cboAno)
        ano.adapter = adapter

        ok.setOnClickListener {
            method.invoke(activity)
            dialog.dismiss()
        }
    }

    fun definirData(mes_: Int, ano_: Int) {
        mes.setSelection(mes_ - 1)
        ano.setSelection(ano_)
    }

    fun showDialog() {
        dialog.show()
    }

    fun getMesNum() = mes.selectedItemPosition + 1
    fun getMes() = mes.selectedItem.toString()
    fun getAno() = ano.selectedItem.toString().drop(2).toInt()

    fun getResult() : String {
        val mes = mes.selectedItem.toString()
        val ano = ano.selectedItem.toString().drop(2)
        return "$mes/$ano"
    }
}
package com.anthony.projeto_controlefinanceiro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MovimentacaoAdapter(contexto: Context) : ArrayAdapter<Movimentacao>(contexto, 0) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v: View

        if (convertView != null) v = convertView
        else v = LayoutInflater.from(context).inflate(R.layout.list_movimentacao_view_item, parent, false)

        val item = getItem(position)!!
        val tipo = if (item.tipo == 0) "+" else "-"
        val nome = v.findViewById<TextView>(R.id.lblItem_V)
        val data = v.findViewById<TextView>(R.id.lblData_V)
        val valor = v.findViewById<TextView>(R.id.lblValor_V)

        nome.text = "[$tipo] ${item.nome}"
        data.text = item.data
        valor.text = context.formatter.format(item.valor)

        return v
    }
}
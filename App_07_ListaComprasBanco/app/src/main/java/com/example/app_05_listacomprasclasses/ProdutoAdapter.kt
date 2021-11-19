package com.example.app_05_listacomprasclasses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.text.NumberFormat
import java.util.*

class ProdutoAdapter(contexto: Context): ArrayAdapter<Produto>(contexto, 0) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v: View

        if (convertView != null) v = convertView
        else v = LayoutInflater.from(context).inflate(R.layout.list_view_item, parent, false)

        val item = getItem(position)
        val txtNome = v.findViewById<TextView>(R.id.lblNomeProduto)
        val txtQuantidade = v.findViewById<TextView>(R.id.lblQntProduto)
        val txtPreco = v.findViewById<TextView>(R.id.lblPrecoProduto)
        val imgProduto = v.findViewById<ImageView>(R.id.imgFotoProduto)
        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))

        txtNome.text = item?.nome
        txtQuantidade.text = "QNT: ${item?.qnt.toString()}"
        txtPreco.text = f.format(item?.preco)
        if (item?.foto != null) imgProduto.setImageBitmap(item.foto)
        return v
    }
}
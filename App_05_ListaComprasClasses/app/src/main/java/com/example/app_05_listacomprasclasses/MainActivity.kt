package com.example.app_05_listacomprasclasses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val produtosAdapter = ProdutoAdapter(this)
        val lstProdutos = findViewById<ListView>(R.id.lstProdutos)
        lstProdutos.adapter = produtosAdapter

        btnAdicionar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        lstProdutos.setOnItemClickListener { adapterView: AdapterView<*>, view, position, id ->
            val item = produtosAdapter.getItem(position)
            produtosAdapter.remove(item)
            produtosGlobal.remove(item)
            atualizarTotal()
        }
    }

    override fun onResume() {
        super.onResume()

        val adapter = lstProdutos.adapter as ProdutoAdapter
        adapter.clear()
        adapter.addAll(produtosGlobal)

        atualizarTotal()
    }

    fun atualizarTotal() {
        var soma = 0.0
        for(item in produtosGlobal)
            soma += item.preco * item.qnt

        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        txtTotal.text = "TOTAL: ${f.format(soma)}"
    }
}
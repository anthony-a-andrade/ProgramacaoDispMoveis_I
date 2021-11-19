package com.example.app_05_listacomprasclasses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
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
            startActivity<CadastroActivity>()
        }

        lstProdutos.setOnItemClickListener { adapterView: AdapterView<*>, view, position, id ->
            val item = produtosAdapter.getItem(position)
            produtosAdapter.remove(item)
            database.use {
                delete("produtos", "id = {ID}", "ID" to item?.id.toString())
            }
            atualizarDados()
        }
    }

    override fun onResume() {
        super.onResume()
        atualizarDados()
    }

    fun atualizarDados() {
        val adapter = lstProdutos.adapter as ProdutoAdapter
        database.use {
            select("produtos").exec {
                var parser = rowParser { id: Int, nome: String, quantidade: Int, valor: Double, foto: ByteArray? ->
                    Produto(id, nome, quantidade, valor, foto?.toBitmap())
                }

                val listaProdutos = parseList(parser)
                adapter.clear()
                adapter.addAll(listaProdutos)

                var soma = 0.0
                for(item in listaProdutos)
                    soma += item.preco * item.qnt

                val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
                txtTotal.text = "TOTAL: ${f.format(soma)}"
            }
        }
    }
}
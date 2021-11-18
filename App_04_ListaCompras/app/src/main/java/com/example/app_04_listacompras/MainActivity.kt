package com.example.app_04_listacompras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lstItens = findViewById<ListView>(R.id.lstItens)
        val txtItem = findViewById<EditText>(R.id.txtNomeProduto)
        val btnInserir = findViewById<Button>(R.id.btnInserir)

        val itensAdapter = ArrayAdapter<String>(this, R.layout.list_item)
        lstItens.adapter = itensAdapter

        btnInserir.setOnClickListener {
            val item = txtItem.text.toString()

            if (item.isEmpty()) txtItem.error = "Insira um item!"
            else {
                itensAdapter.add(item)
                txtItem.text.clear()
            }
        }

        lstItens.setOnItemClickListener { adapterView: AdapterView<*>, view, position: Int, id: Long ->
            val item = itensAdapter.getItem(position)
            itensAdapter.remove(item)
        }
    }
}
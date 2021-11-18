package com.example.app_05_listacomprasclasses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.activity_cadastro.*
import android.widget.Toast

class CadastroActivity : AppCompatActivity() {
    val COD_IMAGE = 101
    var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        btnAdicionar.setOnClickListener {
            val nome = txtNome.text.toString()
            val qnt = txtQuantidade.text.toString()
            val preco = txtPreco.text.toString()

            if (nome.isEmpty()) txtNome.error = "Digite o nome do produto!"
            else if (qnt.isEmpty()) txtQuantidade.error = "Digite a quantidade do produto!"
            else if (preco.isEmpty()) txtPreco.error = "Digite o preço do produto!"
            else {
                val produto = Produto(nome, qnt.toInt(), preco.toDouble(), imageBitmap)
                produtosGlobal.add(produto)

                let {
                    txtNome.text.clear()
                    txtQuantidade.text.clear()
                    txtPreco.text.clear()
                    imgProduto.setImageResource(android.R.drawable.ic_menu_camera)
                    imageBitmap = null
                }

                Toast.makeText(applicationContext, "Produto adicionado", Toast.LENGTH_SHORT).show()
            }
        }

        imgProduto.setOnClickListener {
            abrirGaleria()
        }
    }

    fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                let {
                    val inputStream = contentResolver.openInputStream(data.data!!)
                    imageBitmap = BitmapFactory.decodeStream(inputStream)
                    imgProduto.setImageBitmap(imageBitmap)
                }
            }
        }
    }
}
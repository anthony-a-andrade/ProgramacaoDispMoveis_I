package com.example.app_05_listacomprasclasses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_cadastro.btnAdicionar
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast
import java.text.NumberFormat
import java.util.*

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
                database.use {
                    val idProduto = insert("Produtos",
                        "nome" to nome,
                        "quantidade" to qnt.toInt(),
                        "valor" to preco.toDouble(),
                        "foto" to imageBitmap?.toByteArray()
                    )

                    if (idProduto == -1L) toast("Erro ao inserir no banco de dados!")
                    else {
                        let {
                            txtNome.text.clear()
                            txtQuantidade.text.clear()
                            txtPreco.text.clear()
                            imgProduto.setImageResource(android.R.drawable.ic_menu_camera)
                            imageBitmap = null
                        }

                        toast("Produto adicionado!")
                    }
                }
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
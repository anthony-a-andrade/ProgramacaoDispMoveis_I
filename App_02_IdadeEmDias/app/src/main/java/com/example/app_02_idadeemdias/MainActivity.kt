package com.example.app_02_idadeemdias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtAnos = findViewById<EditText>(R.id.txtAnos)
        val txtMeses = findViewById<EditText>(R.id.txtMeses)
        val txtDias = findViewById<EditText>(R.id.txtDias)
        val btnCalcular = findViewById<Button>(R.id.btnConverter)
        val lblResultado = findViewById<TextView>(R.id.lblResultado)

        btnCalcular.setOnClickListener {
            val anosEmpty = txtAnos.text.isEmpty()
            val mesesEmpty = txtMeses.text.isEmpty()
            val diasEmpty = txtDias.text.isEmpty()

            if (anosEmpty || mesesEmpty || diasEmpty) {
                if (anosEmpty) txtAnos.error = "Digite a quantidade de anos!"
                if (mesesEmpty) txtMeses.error = "Digite a quantidade de meses!"
                if (diasEmpty) txtDias.error = "Digite a quantidade de dias!"
            } else {
                val anos = txtAnos.text.toString().toInt()
                val meses = txtMeses.text.toString().toInt()
                val dias = txtDias.text.toString().toInt()
                val result = (anos * 360) + (meses * 30) + dias
                lblResultado.text = "Ao converter sua idade,\nvocê tem $result dia(s)!"
            }
        }
    }
}
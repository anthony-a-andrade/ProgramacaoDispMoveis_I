package com.example.app_01_calculoaposentadoria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.get
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spn_sexo = findViewById<Spinner>(R.id.spn_sexo);
        val txt_idade = findViewById<EditText>(R.id.txt_idade);
        val btn_calcular = findViewById<Button>(R.id.btn_calcular);
        val txt_resultado = findViewById<TextView>(R.id.txt_resultado);

        spn_sexo.adapter = ArrayAdapter<String>(
                this,
                R.layout.spinner_item,
                listOf("Masculino", "Feminino"))

        btn_calcular.setOnClickListener {
            try {
                val sexo = spn_sexo.selectedItem as String
                val idade = txt_idade.text.toString().toInt()
                var resultado = 0

                if (sexo == "Masculino") resultado = 65 - idade
                else if (sexo == "Feminino") resultado = 62 - idade

                if (resultado > 0) txt_resultado.text = "Falta(m) $resultado ano(s) para você se aposentar!"
                else txt_resultado.text = "Você já tem direito a aposentadoria!"
            }
            catch (e : Exception) {
                txt_idade.error = "Digite um valor válido para a idade!"
            }
        }
    }
}
package com.example.app_03_celsiusfahrenheit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtC = findViewById<EditText>(R.id.txtCelsius)
        val txtF = findViewById<EditText>(R.id.txtFahrenheit)
        val txtK = findViewById<EditText>(R.id.txtKelvin)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val btnLimpar = findViewById<Button>(R.id.btnLimpar)

        btnCalcular.setOnClickListener {
            val txtC_hasText = txtC.text.isNotEmpty()
            val txtF_hasText = txtF.text.isNotEmpty()
            val txtK_hasText = txtK.text.isNotEmpty()
            var amount = 0
            if (txtC_hasText) amount++
            if (txtF_hasText) amount++
            if (txtK_hasText) amount++

            if (amount == 1) {
                val C : Double
                val F : Double
                val K : Double

                if (txtC_hasText)
                {
                    C = txtC.text.toString().toDouble()
                    F = (9 * C + 160) / 5
                    K = C + 273.15
                }
                else if (txtF_hasText)
                {
                    F = txtF.text.toString().toDouble()
                    C = (F * 5 - 160) / 9
                    K = C + 273.15
                }
                else
                {
                    K = txtK.text.toString().toDouble()
                    C = K - 273.15
                    F = (9 * C + 160) / 5
                }

                val df = DecimalFormat("0.0000")
                df.roundingMode = RoundingMode.CEILING

                txtC.setText(df.format(C).toString())
                txtF.setText(df.format(F).toString())
                txtK.setText(df.format(K).toString())
            } else {
                var msg = "Informe "
                msg += if (amount == 0) "uma das temperaturas" else "somente uma temperatura"
                msg += " para realizar a conversão!"

                Snackbar.make(it, msg, Snackbar.LENGTH_LONG).show()
            }
        }

        btnLimpar.setOnClickListener {
            txtC.text.clear()
            txtF.text.clear()
            txtK.text.clear()
        }
    }
}
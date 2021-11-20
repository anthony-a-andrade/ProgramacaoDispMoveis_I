package com.example.app_08_acessandoapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.core.view.children
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.forEachChild
import org.json.JSONObject
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {
    val API_BTC = "https://www.mercadobitcoin.net/api/BTC/ticker/"
    val API_USD = "https://economia.awesomeapi.com.br/last/USD"
    val API_BRL = "https://economia.awesomeapi.com.br/last/BRL-USD"

    var cotacaoReal : Double = 0.0 // = Dolares necessários para 1 real
    var cotacaoDolar : Double = 0.0 // = Reais necessários para 1 dólar
    var cotacaoBitcoin : Double = 0.0 // = Reais necessários para 1 bitcoin
    var cotacaoAtual : Double = 0.0

    val client = OkHttpClient.Builder()
            .connectionSpecs(Arrays.asList(ConnectionSpec.COMPATIBLE_TLS))
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rdgEntrada.setOnCheckedChangeListener { rdb, id ->
            rdgSaida.forEachChild { (it as RadioButton).isChecked = false }
            lblTipoResultado.isVisible = false
            lblResultado.text = ""

            rdbReal_O.isEnabled = !rdbReal_I.isChecked
            rdbDolar_O.isEnabled = !rdbDolar_I.isChecked
            rdbBitcoin_O.isEnabled = !rdbBitcoin_I.isChecked
        }

        rdgSaida.setOnCheckedChangeListener { rdb, id ->
            lblTipoResultado.isVisible = false
            lblResultado.text = ""

            val entrada_ = rdgEntrada.children.filter { (it as RadioButton).isChecked }
            val saida_ = rdgSaida.children.filter { (it as RadioButton).isChecked }

            val entrada = if (entrada_.none()) "" else (entrada_.first() as RadioButton).text[0]
            val saida = if (saida_.none()) "" else (saida_.first() as RadioButton).text[0]

            cotacaoAtual = when (entrada.toString() + saida.toString()) {
                "RD" -> cotacaoDolar
                "RB" -> cotacaoBitcoin
                "DR" -> cotacaoReal
                "DB" -> cotacaoBitcoin / cotacaoDolar
                "BR" -> 1 / cotacaoBitcoin
                "BD" -> cotacaoDolar * (1 / cotacaoBitcoin)
                else -> 0.0
            }

            lblCotacao.text =
                    when (entrada.toString() + saida.toString()) {
                        "RD" -> "1 USD = $cotacaoAtual BRL"
                        "RB" -> "1 BTC = $cotacaoAtual BRL"
                        "DR" -> "1 BRL = $cotacaoAtual USD"
                        "DB" -> "1 BTC = $cotacaoAtual USD"
                        "BR" -> "1 BRL = $cotacaoAtual BTC"
                        "BD" -> "1 USD = $cotacaoAtual BTC"
                        else -> ""
                    }
        }

        buscarCotacoes()

        btnConverter.setOnClickListener {
            calcular()
        }
    }

    private fun buscarCotacoes() {
        client.doAsync {
            val resposta = URL(API_BRL).readText()
            cotacaoReal = JSONObject(resposta).getJSONObject("BRLUSD").getDouble("bid")
        }

        client.doAsync {
            val resposta = URL(API_USD).readText()
            cotacaoDolar = JSONObject(resposta).getJSONObject("USDBRL").getDouble("bid")
        }

        client.doAsync {
            val resposta = URL(API_BTC).readText()
            cotacaoBitcoin = JSONObject(resposta).getJSONObject("ticker").getDouble("last")
        }
    }

    fun calcular() {
        if (txtEntrada.text.isEmpty()) {
            txtEntrada.error = "Preencha um valor!"
            return
        }

        lblTipoResultado.isVisible = true
        lblTipoResultado.text = "Quantidade de ${
            if (rdbReal_O.isChecked) "Reais"
            else if (rdbDolar_O.isChecked) "Dólares"
            else "Bitcoins"}"

        val valorEntrada = txtEntrada.text.toString().toDouble()
        val resultado = valorEntrada / cotacaoAtual
        lblResultado.text = "$resultado ${
            if (rdbReal_O.isChecked) "BRL"
            else if (rdbDolar_O.isChecked) "USD"
            else "BTC"}"
    }
}
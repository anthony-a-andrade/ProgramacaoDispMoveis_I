package com.anthony.projeto_controlefinanceiro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.bloco_menu.*
import kotlinx.android.synthetic.main.bloco_menu_add_mov.*
import kotlinx.android.synthetic.main.bloco_menu_home.*
import kotlinx.android.synthetic.main.bloco_menu_listar_mov.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class ListActivity : AppCompatActivity() {
    private lateinit var adapter: MovimentacaoAdapter
    private lateinit var dateDialog: MonthYearPickerDialog
    private var filtrar = false
    private var editar = false
    private var excluir = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        Menu(this, pnlHome, pnlAddMovimentacao, pnlListarMovimentacao, btnExpandir, pnlListarMovimentacao).enable()

        val aplicarFiltro = ListActivity::class.java.getMethod("aplicarFiltro")
        dateDialog = MonthYearPickerDialog(this, aplicarFiltro)

        adapter = MovimentacaoAdapter(this)
        lstMovimentacoes.adapter = adapter
        carregarDados()

        btnFiltrar.setOnClickListener { dateDialog.showDialog() }
        btnEditar.setOnClickListener { estadoAcao(!editar, false) }
        btnExcluir.setOnClickListener { estadoAcao(false, !excluir) }

        btnLimpar.setOnClickListener {
            btnLimpar.visibility = View.GONE
            filtrar = false
            carregarDados()
        }

        lstMovimentacoes.setOnItemClickListener { parent, view, position, id ->
            val item = adapter.getItem(position)!!
            val id = item.id

            if (editar)
            {
                movId = id
                startActivity<EditActivity>()
            }

            if (excluir)
            {
                toast(controller.delete(id))
                carregarDados()
            }
        }
    }

    private fun estadoAcao(edit: Boolean, del: Boolean) {
        editar = edit
        excluir = del

        btnEditar.setBackgroundResource(if(editar) R.drawable.bg_border else 0)
        btnExcluir.setBackgroundResource(if(excluir) R.drawable.bg_border else 0)
    }

    fun aplicarFiltro() {
        filtrar = true
        btnLimpar.visibility = View.VISIBLE
        carregarDados()
    }

    private fun carregarDados() {
        var text = "MOVIMENTAÇÕES"
        val data = dateDialog.getResult()

        if (filtrar) text += "\n[$data]"
        lblTitulo.text = text

        doAsync {
            uiThread {
                adapter.clear()

                if (filtrar) adapter.addAll(controller.findAllByDate(data)!!)
                else adapter.addAll(controller.findAll()!!)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        pnlHome.performClick()
    }
}
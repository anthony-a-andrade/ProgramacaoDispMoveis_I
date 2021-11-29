package com.anthony.projeto_controlefinanceiro

import org.jetbrains.anko.db.*

class MovimentacoesController(val db: MovimentacoesDatabase) {
    fun save(nome: String, valor: Double, mes: Int, ano: Int, data: String, tipo: Int) : String {
        var id = -1L
        db.use {
            id = insert("Movimentacoes",
                "nome" to nome,
                "valor" to valor,
                "mes" to mes,
                "ano" to ano,
                "data" to data,
                "tipo" to tipo,
            )
        }
        return if (id == -1L) "Erro ao salvar!" else "Salvo com sucesso!"
    }

    fun findAll() : List<Movimentacao>? {
        var result: List<Movimentacao>? = null
        db.use {
            select("Movimentacoes")
                .orderBy("ano, mes, nome, tipo, valor")
                .exec {
                    val parser = rowParser {
                            id: Int,
                            nome: String,
                            valor: Double,
                            data: String,
                            mes: Int,
                            ano: Int,
                            tipo: Int
                        -> Movimentacao(id, nome, valor, data, mes, ano, tipo)
                    }
                    result = parseList(parser)
                }
        }
        return result
    }

    fun findAllByDate(date: String) : List<Movimentacao>? {
        var result: List<Movimentacao>? = null
        db.use {
            select("Movimentacoes")
                .whereArgs("data = {_date}", "_date" to date)
                .orderBy("ano, mes, nome, tipo, valor")
                .exec {
                    val parser = rowParser {
                            id: Int,
                            nome: String,
                            valor: Double,
                            data: String,
                            mes: Int,
                            ano: Int,
                            tipo: Int
                        -> Movimentacao(id, nome, valor, data, mes, ano, tipo)
                    }
                    result = parseList(parser)
                }
        }
        return result
    }

    fun delete(id: Int) : String {
        var _id = -1
        db.use { _id = delete("Movimentacoes", "id = {_id}", "_id" to id) }
        return if (_id == -1) "Erro ao deletar!" else "Deletado com sucesso!"
    }

    fun findById(id: Int) : Movimentacao? {
        var mov : Movimentacao? = null
        db.use {
            select("Movimentacoes")
                .whereArgs("id = {_id}", "_id" to id)
                .limit(1)
                .exec {
                    val parser = rowParser {
                            id: Int,
                            nome: String,
                            valor: Double,
                            data: String,
                            mes: Int,
                            ano: Int,
                            tipo: Int
                        -> Movimentacao(id, nome, valor, data, mes, ano, tipo)
                    }
                    mov = parseSingle(parser)
                }
        }
        return mov
    }

    fun update(id: Int, nome: String, valor: Double, mes: Int, ano: Int, data: String, tipo: Int) {
        db.use {
            update("Movimentacoes", "nome" to nome, "valor" to valor, "mes" to mes, "ano" to ano, "data" to data, "tipo" to tipo)
                .whereArgs("id = {_id}", "_id" to id)
                .exec()
        }
    }
}
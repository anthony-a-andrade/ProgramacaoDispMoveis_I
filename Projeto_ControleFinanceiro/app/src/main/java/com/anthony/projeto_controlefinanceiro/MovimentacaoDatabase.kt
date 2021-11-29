package com.anthony.projeto_controlefinanceiro

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MovimentacoesDatabase(context: Context) : ManagedSQLiteOpenHelper(ctx = context, name = "movimentacoes.db", version = 1) {
    companion object {
        private var instance: MovimentacoesDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context) : MovimentacoesDatabase {
            return if (instance == null) MovimentacoesDatabase(ctx.applicationContext) else instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("Movimentacoes", true,
            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
            "nome" to TEXT,
            "valor" to REAL,
            "data" to TEXT,
            "mes" to INTEGER,
            "ano" to INTEGER,
            "tipo" to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) { }
}
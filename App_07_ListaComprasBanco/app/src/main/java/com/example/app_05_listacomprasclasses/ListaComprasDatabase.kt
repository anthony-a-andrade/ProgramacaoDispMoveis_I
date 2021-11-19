package com.example.app_05_listacomprasclasses

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

val Context.database: ListaComprasDatabase
    get() = ListaComprasDatabase.getInstance(applicationContext)

class ListaComprasDatabase (context: Context) :ManagedSQLiteOpenHelper(ctx = context, name = "listaCompras.db", version = 1) {
    companion object {
        private var instance: ListaComprasDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): ListaComprasDatabase {
            return if (instance == null) ListaComprasDatabase(ctx.applicationContext) else instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.createTable("Produtos", true,
            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
            "nome" to TEXT,
            "quantidade" to INTEGER,
            "valor" to REAL,
            "foto" to BLOB
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // atualização banco
    }
}
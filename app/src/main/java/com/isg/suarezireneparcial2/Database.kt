package com.isg.suarezireneparcial2

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    // Query para crear la tabla de login
    private val CREATE =
        "create table usuarios(usuario TEXT, contraseña TEXT)"

    // Query para eliminar la tabla si ya existe, realmente no es necesario pero tengo costumbre de hacerlo de 1º
    private val NUKE_ALL = "drop table if exists usuarios"

    override fun onCreate(db: SQLiteDatabase) {
        // Se crea la base de datos y se insertan los usuarios precargados
        db.execSQL(CREATE)
        db.execSQL("INSERT INTO usuarios (usuario, contraseña) VALUES ('admin', 'admin')")
        db.execSQL("INSERT INTO usuarios (usuario, contraseña) VALUES ('invitado', 'guest')")
        db.execSQL("INSERT INTO usuarios (usuario, contraseña) VALUES ('user', 'user')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Se elimina la base de datos si ya existe. Véase nota en la línea 15
        db.execSQL(NUKE_ALL)
        this.onCreate(db)
    }

    // Verificación de usuario y contraseña válidos
    fun login(usuario: String, contraseña: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?",
            arrayOf(usuario, contraseña)
        )

        val valid = cursor.count > 0
        cursor.close()
        return valid
    }
}
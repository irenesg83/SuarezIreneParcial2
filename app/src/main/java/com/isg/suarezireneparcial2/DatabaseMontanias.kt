package com.isg.suarezireneparcial2

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseMontanias(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    // Query para crear la tabla de montañas
    private val CREATE =
        """
        create table montanias (
            nombre text not null,
            usuario text not null,
            altura integer not null,
            primary key (nombre, usuario)
        )
        """

    // Query para eliminar la tabla si ya existe, no es necesario pero lo pongo por costumbre
    private val NUKE_ALL = "drop table if exists montanias"

    override fun onCreate(db: SQLiteDatabase) {
        // Crear la tabla de montañas
        db.execSQL(CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Eliminar la tabla si ya existe y crear una nueva
        db.execSQL(NUKE_ALL)
        this.onCreate(db)
    }

    // Insertar una montaña en la base de datos
    fun insertarMontania(nombre: String, usuario: String, altura: Int) {
        val db = this.writableDatabase
        val query = """insert into montanias (nombre, usuario, altura)
            values (?, ?, ?)"""
        db.execSQL(query, arrayOf(nombre, usuario, altura))
    }

    // Obtener las montañas de un usuario específico
    fun obtener(usuario: String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("select * from montanias where usuario = ?", arrayOf(usuario))
    }

    // Eliminar una montaña específica para un usuario
    fun delMontania(nombre: String, usuario: String) {
        val db = this.writableDatabase
        db.execSQL("delete from montanias where nombre = ? and usuario = ?", arrayOf(nombre, usuario))
    }

    // Modificar la altura de una montaña
    fun modAltura(nombre: String, usuario: String, nuevaAltura: Int) {
        val db = this.writableDatabase
        val query = """update montanias
            set altura = ? where nombre = ? and usuario = ?"""
        db.execSQL(query, arrayOf(nuevaAltura, nombre, usuario))
    }

    // Obtener el número de montañas de un usuario específico
    fun count(usuario: String): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("select count(*) from i where usuario = ?", arrayOf(usuario))
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count
    }
}

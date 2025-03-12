package com.isg.suarezireneparcial2

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Montanias : AppCompatActivity() {

    private lateinit var db: DatabaseMontanias
    private lateinit var listView: ListView
    private lateinit var addButton: Button
    private lateinit var un: String
    private var isAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_montanias)

        // Obtener el nombre de usuario y si es admin
        un = intent.getStringExtra("username") ?: ""
        isAdmin = un == "admin"

        db = DatabaseMontanias(this, "montanias.db", null, 1)

        listView = findViewById(R.id.listViewMontanias)
        addButton = findViewById(R.id.addButton)

        if (!isAdmin) {
            addButton.visibility = Button.GONE
        }

        // Mostrar las montañas de un usuario
        cargarMontanias()

        // Agregar montaña
        addButton.setOnClickListener {
            dialogoAdd()
        }
    }

    // Fun cargar montañas
    @SuppressLint("Range")
    private fun cargarMontanias() {
        val cursor = db.obtener(un)
        val montaniasList = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
            val altura = cursor.getInt(cursor.getColumnIndex("altura"))
            montaniasList.add("$nombre - $altura metros")
        }
        cursor.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, montaniasList)
        listView.adapter = adapter

        // Se actualiza el contador
        val montaniasConquistadas = montaniasList.size
        findViewById<TextView>(R.id.conquistadasTextView).text = "Nº de cimas conquistadas: $montaniasConquistadas"

        // Popup
        listView.setOnItemClickListener { _, _, position, _ ->
            val montana = montaniasList[position]
            val nombre = montana.split(" - ")[0]
            mostrarMenuPopup(nombre)
        }
    }

    // Popup de modificar/eliminar
    private fun mostrarMenuPopup(nombreMontana: String) {
        val options = arrayOf("Modificar montaña", "Eliminar montaña")
        val builder = AlertDialog.Builder(this)
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> dialogoMod(nombreMontana)
                1 -> dialogoDel(nombreMontana)
            }
        }
        builder.show()
    }

    // Añadir
    private fun dialogoAdd() {
        val dialogView = layoutInflater.inflate(R.layout.add_montania, null)
        val nombreEdit = dialogView.findViewById<EditText>(R.id.nombreEdit)
        val alturaEdit = dialogView.findViewById<EditText>(R.id.alturaEdit)
        val usuarioSpinner = dialogView.findViewById<Spinner>(R.id.usuarioSpinner)

        val usuarios = listOf("invitado", "user")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, usuarios)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        usuarioSpinner.adapter = adapter

        val dialog = AlertDialog.Builder(this)
            .setTitle("Añadir montaña")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = nombreEdit.text.toString()
                val altura = alturaEdit.text.toString().toIntOrNull()

                if (nombre.isNotEmpty() && altura != null) {
                    val usuario = usuarioSpinner.selectedItem.toString()
                    db.insertarMontania(nombre, usuario, altura)
                    cargarMontanias()
                } else {
                    Toast.makeText(this, "Nombre y altura son obligatorios", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }

    // Modificar
    private fun dialogoMod(nombre: String) {
        val dialogView = layoutInflater.inflate(R.layout.add_montania, null)
        val nombreEditText = dialogView.findViewById<EditText>(R.id.nombreEdit)
        val alturaEditText = dialogView.findViewById<EditText>(R.id.alturaEdit)

        nombreEditText.setText(nombre)
        nombreEditText.isEnabled = false

        val dialog = AlertDialog.Builder(this)
            .setTitle("Modificar montaña")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val nuevaAltura = alturaEditText.text.toString().toIntOrNull()

                if (nuevaAltura != null) {
                    db.modAltura(nombre, un, nuevaAltura)
                    cargarMontanias()
                } else {
                    Toast.makeText(this, "Altura inválida", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }

    //Borrar
    private fun dialogoDel(nombre: String) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Eliminar montaña")
            .setMessage("¿Estás seguro de que quieres eliminar la montaña $nombre?")
            .setPositiveButton("Sí") { _, _ ->
                db.delMontania(nombre, un)
                cargarMontanias()
            }
            .setNegativeButton("No", null)
            .create()

        dialog.show()
    }
}

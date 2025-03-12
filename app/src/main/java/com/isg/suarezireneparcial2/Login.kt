package com.isg.suarezireneparcial2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var db: Database = Database(this,"usuarios",null,1)

        var un: EditText = findViewById(R.id.editUN)
        var pw: EditText = findViewById(R.id.editPW)
        var btnLogin: Button = findViewById(R.id.loginButton)

        btnLogin.setOnClickListener {
            val un = un.text.toString()
            val pw = pw.text.toString()

            if (un.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show()
            } else {
                if (db.login(un, pw)) {
                    val intent = Intent(this, Montanias::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Login fallido
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Error de Login")
                        .setMessage("El usuario "+un+" con la contraseña "+pw+" no existe, o las credenciales son incorrectas.")
                        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
            }
        }
    }
}
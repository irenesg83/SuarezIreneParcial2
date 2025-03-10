package com.isg.suarezireneparcial2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val buttonCalc = findViewById<ImageButton>(R.id.btnCalc)
        val buttonMont = findViewById<Button>(R.id.btnMontanas)

        buttonCalc.setOnClickListener{

            val intent= Intent(this, Calculadora::class.java)
            startActivity(intent)

        }

        buttonMont.setOnClickListener{

            val intent=Intent(this,Login::class.java)
            startActivity(intent)

        }

    }


}

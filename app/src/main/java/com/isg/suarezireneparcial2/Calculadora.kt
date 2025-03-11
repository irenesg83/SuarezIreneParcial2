package com.isg.suarezireneparcial2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Calculadora : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculadora)

        // Valores para almacenar los números seleccionados
        var numActual = ""
        var ultimoNum = ""
        var operador = ""

        // TextView de la calculadora
        val displaycalc = findViewById<TextView>(R.id.displayCalc)

        // Botones
        val btn0 = findViewById<Button>(R.id.btn0)
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btn5 = findViewById<Button>(R.id.btn5)
        val btn6 = findViewById<Button>(R.id.btn6)
        val btn7 = findViewById<Button>(R.id.btn7)
        val btn8 = findViewById<Button>(R.id.btn8)
        val btn9 = findViewById<Button>(R.id.btn9)
        val btndel = findViewById<Button>(R.id.btnDel)
        val btninv = findViewById<Button>(R.id.btnInv)
        val btndiv = findViewById<Button>(R.id.btnDividir)
        val btnmult = findViewById<Button>(R.id.btnMult)
        val btnsum = findViewById<Button>(R.id.btnSum)
        val btnres = findViewById<Button>(R.id.btnResta)
        val btncalc = findViewById<Button>(R.id.btnCalc)


        // Lista botones
        val numBotones = listOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0)

        // Se pasa el texto del botón a string y se añade a numactual
        numBotones.forEach { button ->
            button.setOnClickListener {
                numActual += button.text.toString()
                displaycalc.text = numActual
            }
        }

        // Lógica operadores
        if (numActual!= null) {
            btndiv.setOnClickListener {
                ultimoNum = numActual
                numActual = ""
                operador = "/"}
            btnmult.setOnClickListener {
                ultimoNum = numActual
                numActual = ""
                operador = "*"}
            btnsum.setOnClickListener {
                ultimoNum = numActual
                numActual = ""
                operador = "+"}
            btnres.setOnClickListener { ultimoNum = numActual
                numActual = ""
                operador = "-"}
        } else operador = ""

        // Lógica cálculo
        btncalc.setOnClickListener {
            if (numActual.isNotEmpty() && ultimoNum.isNotEmpty() && operador != "") {
                var result = when (operador) {
                    "+" -> (ultimoNum.toInt() + numActual.toInt())
                    "-" -> (ultimoNum.toInt() - numActual.toInt())
                    "x" -> (ultimoNum.toInt() * numActual.toInt())
                    "/" -> (ultimoNum.toInt() / numActual.toInt())
                    else -> 0
                }
                numActual = result.toString()
                displaycalc.text = numActual
                ultimoNum = ""
                operador = ""
            }
        }

        // Función borrar
        btndel.setOnClickListener {
            numActual = ""
            ultimoNum = ""
            operador = ""
            displaycalc.text = "0"
        }

        // Función invertir
        btninv.setOnClickListener {
            if (numActual.isNotEmpty()) {
                numActual = numActual.reversed()
                displaycalc.text = numActual
            }
        }




    }

}
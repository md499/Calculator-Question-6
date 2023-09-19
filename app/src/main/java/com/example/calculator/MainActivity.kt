package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private var calc = ""
    private lateinit var result: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        result = findViewById<EditText>(R.id.result)

        val btns = listOf<Button>(
            findViewById<Button>(R.id.btnZero),
            findViewById<Button>(R.id.btnOne),
            findViewById<Button>(R.id.btnTwo),
            findViewById<Button>(R.id.btnThree),
            findViewById<Button>(R.id.btnFour),
            findViewById<Button>(R.id.btnFive),
            findViewById<Button>(R.id.btnSix),
            findViewById<Button>(R.id.btnSeven),
            findViewById<Button>(R.id.btnEight),
            findViewById<Button>(R.id.btnNine),
            findViewById<Button>(R.id.btnDot),
            findViewById<Button>(R.id.btnAdd),
            findViewById<Button>(R.id.btnMinus),
            findViewById<Button>(R.id.btnMul),
            findViewById<Button>(R.id.btnDiv),
            findViewById<Button>(R.id.btnSqrt)
        )

        btns.forEach { btn ->
            btn.setOnClickListener { addBtn(btn.text.toString()) }
        }

        val btnEqual = findViewById<Button>(R.id.btnEqual)
        val btnClear = findViewById<Button>(R.id.btnClear)

        btnEqual.setOnClickListener { equal(calc) }
        btnClear.setOnClickListener { clear() }

    }

    private fun addBtn(str: String) {
        calc += str
        updateRes()
    }

    private fun clearSqrt(calc: String): String {
        return calc.replace("sqrt", "s")
    }

    private fun equal(calc: String) {
        var calculation = result.text.toString()
        var currentNumber = StringBuilder()
        var currentOperator = '+'
        var total = 0.0
        var isDecimal = false

        for (char in calculation) {
            when {
                char.isDigit() -> {
                    if (isDecimal) {
                        currentNumber.append(char)
                    } else {
                        currentNumber.append(char)
                    }
                }

                char == '.' -> {
                    isDecimal = true
                    currentNumber.append(char)
                }

                char == 's' -> {
                    if (currentNumber.isNotEmpty()) {
                        val number = currentNumber.toString().toDouble()
                        Log.d("num", "$number")
                        Log.d("char", "$char")
                        Log.d("total", "$total")

                        // Apply the square root operation as a unary operator
                        var squareRootResult = sqrt(number)

                        total += squareRootResult

                        // Reset the current operator to none to handle any
                        //arithmetic after root:NOT WORKING

                        currentOperator = ' '

                        currentNumber = StringBuilder()
                        isDecimal = false
                    }
                }

                char in "+-*/" -> {
                    if (currentNumber.isNotEmpty()) {
                        val number = currentNumber.toString().toDouble()
                        when (currentOperator) {
                            '+' -> total += number
                            '-' -> total -= number
                            '*' -> total *= number
                            '/' -> total /= number
                        }
                        currentNumber = StringBuilder()
                        currentOperator = char
                        isDecimal = false
                    }
                }

                else -> {}
            }
        }

        if (currentNumber.isNotEmpty()) {
            val number = currentNumber.toString().toDouble()
            when (currentOperator) {
                '+' -> total += number
                '-' -> total -= number
                '*' -> total *= number
                '/' -> total /= number
                else -> total = number
            }
        }

        result.setText(total.toString())
    }

    private fun clear() {
        calc = ""
        updateRes()
    }

    private fun updateRes() {
        result.setText(calc)
    }


}

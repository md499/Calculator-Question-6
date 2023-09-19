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

        btnEqual.setOnClickListener { equal() }
        btnClear.setOnClickListener { clear() }
    }

    private fun addBtn(str: String) {
        val currentText = result.text.toString()
        result.setText(currentText + str)
    }

    private fun equal() {
        val expression = result.text.toString()
        val sanitizedExpression = expression.replace("squareroot", "sqrt")
        val resultValue = evaluateExpression(sanitizedExpression)
        result.setText(resultValue.toString())
    }

    private fun evaluateExpression(expression: String): Double {
        // You should implement a parser and evaluator for complex expressions here.
        // This code currently handles simple expressions with sqrt and basic operators.
        var currentNumber = ""
        var currentOperator = '+'
        var total = 0.0
        var isNegative = false

        for (char in expression) {
            when {
                char == '-' -> {
                    if (currentNumber.isEmpty()) {
                        isNegative = !isNegative
                    } else {
                        val number = currentNumber.toDouble()
                        total = applyOperator(
                            currentOperator,
                            total,
                            if (isNegative) -number else number
                        )
                        currentNumber = ""
                        isNegative = false
                        currentOperator = '-'
                    }
                }

                char == '+' || char == '*' || char == '/' -> {
                    val number = currentNumber.toDouble()
                    total =
                        applyOperator(currentOperator, total, if (isNegative) -number else number)
                    currentNumber = ""
                    isNegative = false
                    currentOperator = char
                }

                char == 's' -> {
                    if (currentNumber.isNotEmpty()) {
                        val number = currentNumber.toDouble()
                        val squareRootResult = sqrt(if (isNegative) -number else number)
                        total = applyOperator(currentOperator, total, squareRootResult)
                        currentNumber = ""
                        isNegative = false
                        currentOperator = '+'
                    }
                }

                char.isDigit() || char == '.' -> {
                    currentNumber += char
                }
            }
        }

        if (currentNumber.isNotEmpty()) {
            val number = currentNumber.toDouble()
            total = applyOperator(currentOperator, total, if (isNegative) -number else number)
        }

        return total
    }

    private fun applyOperator(operator: Char, operand1: Double, operand2: Double): Double {
        return when (operator) {
            '+' -> operand1 + operand2
            '-' -> operand1 - operand2
            '*' -> operand1 * operand2
            '/' -> {

                if (operand2 != 0.0) {
                    operand1 / operand2
                } else {

                    Double.NaN //
                }
            }

            else -> operand2
        }
    }

    private fun clear() {
        result.setText("")
    }


}


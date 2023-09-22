package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.lang.Exception
import kotlin.math.sqrt
import java.util.Stack

class MainActivity : AppCompatActivity() {

    private var calc = ""
    private lateinit var result: EditText
    val valueStack = Stack<Double>()
    val operatorStack = Stack<Char>()

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

    private fun precedence(op1: Char, op2: Char): Boolean {
        return (op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')
    }



    private fun isOp(ch: Char) : Boolean {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || (ch == 's')
    }


    private fun equal() {
        val expression = result.text.toString()
        if (expression.isEmpty()) {
            Toast.makeText(this, R.string.emptyexp, Toast.LENGTH_SHORT).show()
        } else {
            Log.d("exp", expression)
            val sanitizedExpression = expression.replace("sqrt", "s")
            Log.d("exp", sanitizedExpression)
            val resultValue = parser(sanitizedExpression)
            result.setText(resultValue.toString())
        }
    }

    private fun parser(expression: String) : Double{
        var currentNumber = ""
        var total : Double


        for (ch in expression) {
            when {
                isOp(ch) -> {
                    if (ch == 's') {
                        operatorStack.push(ch)
                    } else {
                        valueStack.push(currentNumber.toDouble())
                        currentNumber = ""
                        if (operatorStack.isEmpty() || precedence(ch, operatorStack.peek())) {
                            operatorStack.push(ch)
                        } else {
                            while (!operatorStack.isEmpty() && precedence(operatorStack.peek(), ch)) {
                                val n = operatorStack.peek()
                                operatorStack.pop()
                                if (n == 's') {
                                    val value = valueStack.peek()
                                    valueStack.push(sqrt(valueStack.pop()))
                                } else {
                                    valueStack.push(applyOperator(n))
                                }
                            }
                            operatorStack.push(ch)
                        }
                    }
                }
                ch.isDigit() || ch == '.' -> {
                    currentNumber += ch
                }
            }
        }
        valueStack.push(currentNumber.toDouble())
        try {
            while (!operatorStack.isEmpty()) {
                val n = operatorStack.peek()
                operatorStack.pop()
                if (n == 's') {
                    var value = valueStack.peek()
                    Log.d("val", value.toString())
                    valueStack.push(sqrt(valueStack.pop()))
                } else {
                    valueStack.push(applyOperator(n))
                }
            }
            total = valueStack.peek()

        } catch (e: Exception) {
            println("Error")
            total = Double.NaN
            print("exception")
        }

        return total


    }

    private fun applyOperator(operator: Char): Double {
        var operand1 : Double
        var operand2 : Double

        if (valueStack.isEmpty()) {
            return Double.NaN
            print("its empty")
        } else {
            operand2 = valueStack.peek()
            valueStack.pop()
        }
        if (valueStack.isEmpty()) {
            return Double.NaN
            print("2 is empty")
        } else {
            operand1 = valueStack.peek()
            valueStack.pop()
        }
        return when (operator) {
            '+' -> operand1 + operand2
            '-' -> operand1 - operand2
            '*' -> operand1 * operand2
            '/' -> {
                if (operand2 != 0.0) {
                    operand1 / operand2
                } else {
                    Toast.makeText(this, R.string.divzero, Toast.LENGTH_SHORT).show()
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
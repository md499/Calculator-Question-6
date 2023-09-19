package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

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
        findViewById<Button>(R.id.btnSqrt))

        btns.forEach{ btn ->
        btn.setOnClickListener{addBtn(btn.text.toString())}}

        val btnEqual = findViewById<Button>(R.id.btnEqual)
        val btnClear = findViewById<Button>(R.id.btnClear)

        btnEqual.setOnClickListener{equal(calc)}
        btnClear.setOnClickListener{clear()}

    }

    /// add append functions

    private fun addBtn(str: String) {
        calc += str
        updateRes()
    }

//    private fun equal() {
//        var total = 0.0
//        var prevnum = 0.0
//        for (char in calc) {
//            char in "+-*/" -> {
//
//            }
////        }
////    }

    private fun equal(calc: String) {
        return
    }

    private fun clear() {
        calc = ""
        updateRes()
    }

    private fun updateRes() {
        result.setText(calc)
    }










}
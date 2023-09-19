package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {
    private lateinit var txt: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt = findViewById(R.id.result)
    }
    fun onNum(view: View)
    {
        txt.append((view as Button).text)
    }
    fun onOperator(view: View){
        txt.append((view as Button).text)
    }
    fun onDot(view: View){
        txt.append((view as Button).text)
    }
    fun onDel(view: View){
        val emptyEditable = Editable.Factory.getInstance().newEditable("")

        // Set the empty Editable as the text of the EditText
        txt.text = emptyEditable
    }
    fun onSqrt(view: View) {
        txt.append("√")
    }

    fun onE(view: View) {
        val text = txt.text.toString()
        val displayText = text.replace("sqrt", "√")
        val sanitizedText = text.replace("√", "sqrt") // Replace "√" with "sqrt" for expression evaluation
        val eval = ExpressionBuilder(sanitizedText).build()
        val res = eval.evaluate()

        // Check for negative square root results
        if (res.isNaN()) {
            txt.text = Editable.Factory.getInstance().newEditable("Error")
        } else {
            txt.text = Editable.Factory.getInstance().newEditable(res.toString())
        }
    }

}
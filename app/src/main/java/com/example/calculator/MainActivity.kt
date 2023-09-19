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

    fun onE(view: View) {
        val text = txt.text.toString()
        val eval = ExpressionBuilder(text).build()
        val res = eval.evaluate()

        // Convert the result to Editable
        val resultText = Editable.Factory.getInstance().newEditable(res.toString())

        txt.text = resultText
    }

}

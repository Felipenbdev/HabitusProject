package com.example.habitus

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.habitus.entities.Usuario

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val buttonCreateUser = findViewById<Button>(R.id.CreateUser)
        val userText = findViewById<EditText>(R.id.InputUser)
        val passwordText = findViewById<EditText>(R.id.InputPassword)
        buttonCreateUser.setOnClickListener { createUser(userText, passwordText) }
    }

    fun createUser(userText: EditText, passwordText: EditText) {
        val usuario = Usuario(1, userText.text.toString(), passwordText.text.toString())
        println(usuario)
    }

}

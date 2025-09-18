package com.example.habitus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val registerbtn = findViewById<Button>(R.id.registerRegisA)
        val usernameText = findViewById<EditText>(R.id.usernameRegisA)
        val passwordText = findViewById<EditText>(R.id.passwordRegistA)
        registerbtn.setOnClickListener { adicionarUsuario(usernameText.text.toString(), passwordText.text.toString()) }
        
        val loginBtn = findViewById<Button>(R.id.loginRegisA)
        loginBtn.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }
    }

    private fun adicionarUsuario(username: String, senha: String) {
        //TODO
    }
}
package com.example.habitus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val loginBtn = findViewById<Button>(R.id.loginLoginA)
        val usernameText = findViewById<EditText>(R.id.usernameLoginA)
        val passwordText = findViewById<EditText>(R.id.passwordLoginA)

        loginBtn.setOnClickListener {
            loginUser(usernameText.text.toString(), passwordText.text.toString())
            val intentHome = Intent(this, MainActivity::class.java)
            startActivity(intentHome)
            finish()
        }
        val registerBtn = findViewById<Button>(R.id.registerLoginA)
        registerBtn.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }
    }

    fun loginUser(username: String, password: String){
        //TODO
    }
}
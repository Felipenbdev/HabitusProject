package com.example.habitus.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.habitus.R
import com.example.habitus.ui.register.RegisterActivity
import com.example.habitus.ui.home.HomeActivity
import com.example.habitus.viewmodel.LoginViewmodel

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Viewmodel iniciado
        viewModel = ViewModelProvider(this)[LoginViewmodel::class.java]

        // Verificar token
        viewModel.validarToken { mensagem ->
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
            val intentHome = Intent(this, HomeActivity::class.java)
            startActivity(intentHome)
            finish()
        }

        // Configuração dos botões e campos
        val loginBtn = findViewById<Button>(R.id.loginLoginA)
        val registerBtn = findViewById<Button>(R.id.registerLoginA)
        val usernameText = findViewById<EditText>(R.id.usernameLoginA)
        val passwordText = findViewById<EditText>(R.id.passwordLoginA)

        // Observers
        viewModel.erro.observe(this) { mensagem ->
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
        }

        // Listeners
        loginBtn.setOnClickListener {
            viewModel.login(usernameText.text.toString().trim(), passwordText.text.toString().trim()) { mensagem ->
                Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
                val intentHome = Intent(this, HomeActivity::class.java)
                startActivity(intentHome)
                finish()
            }
        }
        registerBtn.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }
    }
}
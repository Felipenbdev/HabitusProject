package com.example.habitus.ui.register

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
import com.example.habitus.ui.login.LoginActivity
import com.example.habitus.viewmodel.RegisterViewmodel

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: RegisterViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Viewmodel iniciado
        viewModel = ViewModelProvider(this)[RegisterViewmodel::class.java]

        // Configuração dos botões e campos
        val registerbtn = findViewById<Button>(R.id.registerRegisA)
        val usernameText = findViewById<EditText>(R.id.usernameRegisA)
        val passwordText = findViewById<EditText>(R.id.passwordRegistA)
        val confirmePasswordText = findViewById<EditText>(R.id.confirmPasswordRegisA)
        val loginBtn = findViewById<Button>(R.id.loginRegisA)

        // Observers
        viewModel.erro.observe(this) { mensagem ->
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
        }

        // Listeners
        registerbtn.setOnClickListener {
            viewModel.criarUsuario(usernameText.text.toString(), passwordText.text.toString(), confirmePasswordText.text.toString()) { mensagem ->
                Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
                // Limpa os campos após adicionar
                usernameText.text.clear()
                passwordText.text.clear()
                confirmePasswordText.text.clear()
            }
        }

        loginBtn.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }
    }
}
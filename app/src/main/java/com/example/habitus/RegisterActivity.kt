package com.example.habitus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.habitus.entities.Usuario
import com.example.habitus.network.RetrofitInstance

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
        registerbtn.setOnClickListener { adicionarUsuario(usernameText.text.toString(), passwordText.text.toString(), this) }
        
        val loginBtn = findViewById<Button>(R.id.loginRegisA)
        loginBtn.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }
    }

    private fun adicionarUsuario(username: String, senha: String, context: Context) {
        val novoUsuario = Usuario(username = username, senha = senha)

        val call = RetrofitInstance.api.criarUsuario(novoUsuario)

        call.enqueue(object : retrofit2.Callback<Usuario> {
            override fun onResponse(call: retrofit2.Call<Usuario>, response: retrofit2.Response<Usuario>) {
                if (response.isSuccessful) {
                    val usuarioCriado = response.body()
                    Toast.makeText(context, "Usuário criado: ${usuarioCriado?.username}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Erro ao criar usuário: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: retrofit2.Call<Usuario>, t: Throwable) {
                Toast.makeText(context, "Falha de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
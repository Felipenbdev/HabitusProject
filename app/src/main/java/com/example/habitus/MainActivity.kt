package com.example.habitus

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.habitus.entities.Usuario
import com.example.habitus.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

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
        val buttonShowById = findViewById<Button>(R.id.ShowById)
        val userText = findViewById<EditText>(R.id.InputUser)
        val passwordText = findViewById<EditText>(R.id.InputPassword)
        val IdInput = findViewById<EditText>(R.id.InputId)
        buttonCreateUser.setOnClickListener { adicionarUsuario(userText, passwordText) }
        buttonShowById.setOnClickListener { showById(IdInput.text.toString().toLongOrNull()) }
    }

    private fun adicionarUsuario(username: EditText, senha: EditText) {
        val novoUsuario = Usuario(username = username.text.toString(), senha = senha.text.toString())

        val call = RetrofitInstance.api.criarUsuario(novoUsuario)

        call.enqueue(object : retrofit2.Callback<Usuario> {
            override fun onResponse(call: retrofit2.Call<Usuario>, response: retrofit2.Response<Usuario>) {
                if (response.isSuccessful) {
                    val usuarioCriado = response.body()
                    println("Usuário criado com sucesso: $usuarioCriado")
                } else {
                    println("Erro ao criar usuário: ${response.code()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<Usuario>, t: Throwable) {
                println("Falha na requisição: ${t.message}")
            }
        })
    }

    fun showById(id: Long?) {
        println(id)
    }

}

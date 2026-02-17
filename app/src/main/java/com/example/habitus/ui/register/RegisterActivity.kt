package com.example.habitus.ui.register

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
import androidx.lifecycle.ViewModelProvider
import com.example.habitus.R
import com.example.habitus.model.Usuario
import com.example.habitus.network.RetrofitInstance
import com.example.habitus.ui.login.LoginActivity
import com.example.habitus.viewmodel.HomeViewModel
import com.example.habitus.viewmodel.RegisterViewmodel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val loginBtn = findViewById<Button>(R.id.loginRegisA)

        // Observers
        viewModel.erro.observe(this) { mensagem ->
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
        }

        // Listeners
        registerbtn.setOnClickListener {
            viewModel.criarUsuario(usernameText.text.toString(), passwordText.text.toString()) { mensagem ->
                Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
                // Limpa os campos após adicionar
                usernameText.text.clear()
                passwordText.text.clear()
            }
        }

        loginBtn.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }
    }

//    private fun adicionarUsuario(username: String, senha: String, context: Context) {
//        val novoUsuario = Usuario(username = username, senha = senha)
//
//        val call = RetrofitInstance.api.criarUsuario(novoUsuario)
//
//        call.enqueue(object : Callback<Usuario> {
//            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
//                if (response.isSuccessful) {
//                    val usuarioCriado = response.body()
//                    Toast.makeText(context, "Usuário criado: ${usuarioCriado?.username}", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, "Erro ao criar usuário: ${response.code()}", Toast.LENGTH_SHORT).show()
//                }
//            }
//            override fun onFailure(call: Call<Usuario>, t: Throwable) {
//                Toast.makeText(context, "Falha de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
}
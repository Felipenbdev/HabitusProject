package com.example.habitus.ui.login

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
import com.example.habitus.ui.register.RegisterActivity
import com.example.habitus.model.Usuario
import com.example.habitus.network.RetrofitInstance
import com.example.habitus.ui.home.HomeActivity
import com.example.habitus.viewmodel.LoginViewmodel
import com.example.habitus.viewmodel.RegisterViewmodel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            viewModel.login(usernameText.text.toString(), passwordText.text.toString()) { ->
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

//    fun loginUser(username: String, senha: String, context: Context, onSuccess: (Usuario) -> Unit) {
//        val call = RetrofitInstance.api.login(username, senha)
//
//        call.enqueue(object : Callback<Usuario> {
//            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
//                if (response.isSuccessful) {
//                    val usuarioLogado = response.body()
//                    if (usuarioLogado != null) {
//                        Toast.makeText(
//                            context,
//                            "Login bem-sucedido: ${usuarioLogado.username}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        onSuccess(usuarioLogado)
//                    }
//                } else {
//                    Toast.makeText(context, "Login falhou: ${response.code()}", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//
//            override fun onFailure(call: Call<Usuario>, t: Throwable) {
//                Toast.makeText(context, "Erro de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
//                t.printStackTrace()
//            }
//        })
//    }
}
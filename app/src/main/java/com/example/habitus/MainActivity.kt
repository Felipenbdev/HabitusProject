package com.example.habitus

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.habitus.entities.Tarefa
import com.example.habitus.entities.Usuario
import com.example.habitus.network.RetrofitInstance

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
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
        val buttonAddTarefa = findViewById<Button>(R.id.buttonAddTarefa)

        val userText = findViewById<EditText>(R.id.InputUser)
        val passwordText = findViewById<EditText>(R.id.InputPassword)

        val IdInput = findViewById<EditText>(R.id.InputId)

        val IdInputadd = findViewById<EditText>(R.id.IdAdd)
        val descricaoText = findViewById<EditText>(R.id.Descricao)
        val data = findViewById<EditText>(R.id.Data)

        buttonCreateUser.setOnClickListener { adicionarUsuario(userText, passwordText) }
        buttonShowById.setOnClickListener { showById(IdInput.text.toString().toLong()) }
        buttonAddTarefa.setOnClickListener { addTarefa(IdInputadd.text.toString().toLong(),
            descricaoText.text.toString(),
            data.text.toString()) }
        val view = findViewById<TextView>(R.id.idtarefas)
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

    fun showById(id: Long) {
        val call = RetrofitInstance.api.listarTarefasDoUsuario(id)
        call.enqueue(object : retrofit2.Callback<List<Tarefa>> {
            override fun onResponse(
                call: retrofit2.Call<List<Tarefa>>,
                response: retrofit2.Response<List<Tarefa>>
            ) {
                if (response.isSuccessful) {
                    val tarefas = response.body()
                    if (tarefas != null) {
                        val builder = StringBuilder()
                        builder.append("Tarefas do usuário $id:\n")
                        for (tarefa in tarefas) {
                            builder.append("- ${tarefa.id}: ${tarefa.descricao}\n")
                        }
                        val view = findViewById<TextView>(R.id.idtarefas)
                        view.text = builder.toString()
                    } else {
                        println("Nenhuma tarefa encontrada para o usuário $id.")
                    }
                } else {
                    val view = findViewById<TextView>(R.id.idtarefas)
                    view.text = "Erro na resposta: ${response.code()}"
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Tarefa>>, t: Throwable) {
                val view = findViewById<TextView>(R.id.idtarefas)
                view.text = "Falha na requisição: ${t.message}"
            }
        })
    }

    fun addTarefa(id: Long, descricao: String, data: String) {
    }

}

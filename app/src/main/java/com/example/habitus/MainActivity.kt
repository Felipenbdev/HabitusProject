package com.example.habitus

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.habitus.entities.Tarefa
import com.example.habitus.entities.Usuario
import com.example.habitus.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

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
        val buttonData = findViewById<Button>(R.id.Selectdata)
        val calendario = Calendar.getInstance()

        buttonData.setOnClickListener { showData(calendario) }

        buttonCreateUser.setOnClickListener { adicionarUsuario(userText, passwordText, this) }
        buttonShowById.setOnClickListener { showById(IdInput.text.toString().toLong()) }
        // buttonAddTarefa.setOnClickListener { addTarefa(IdInputadd.text.toString().toLong(),
        //    descricaoText.text.toString(),
        //    data.text.toString(), this) }
        val view = findViewById<TextView>(R.id.idtarefas)
    }

    private fun adicionarUsuario(username: EditText, senha: EditText, context : Context) {
        val novoUsuario = Usuario(username = username.text.toString(), senha = senha.text.toString())

        val call = RetrofitInstance.api.criarUsuario(novoUsuario)

        call.enqueue(object : retrofit2.Callback<Usuario> {
            override fun onResponse(call: retrofit2.Call<Usuario>, response: retrofit2.Response<Usuario>) {
                if (response.isSuccessful) {
                    val usuarioCriado = response.body()
                    println("Usuário criado com sucesso: $usuarioCriado")
                    Toast.makeText(context, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show()
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

    fun addTarefa(id: Long, descricao: String, data: String, context : Context) {
        val usuario = Usuario(id = id, username = "", senha = "")
        val tarefa = Tarefa(
            descricao = descricao,
            datahora = data,
            ativo = true,
            usuario = usuario
        )
        val call = RetrofitInstance.api.criarTarefa(tarefa)

        call.enqueue(object : retrofit2.Callback<Tarefa>{
            override fun onResponse(call: Call<Tarefa?>, response: Response<Tarefa?>) {
                if(response.isSuccessful){
                    val tarefaCriada = response.body()
                    println("Tarefa criada com sucesso: ${tarefaCriada?.descricao}")
                    Toast.makeText(context, "Tarefa criado com sucesso!", Toast.LENGTH_SHORT).show()
                }else{
                    println("Erro ao criar tarefa: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Tarefa?>, t: Throwable) {
                println("Falha na requisição: ${t.message}")
            }
        })
    }

    fun showData(calendario: Calendar) {
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, anoEscolhido, mesEscolhido, diaEscolhido ->
            val textData =
                "Data escolhida: $diaEscolhido/${mesEscolhido + 1}/$anoEscolhido"
            println(textData)
            showHora(calendario)
        }, ano, mes, dia).show()
    }

    fun showHora(calendario: Calendar) {
        val hora = calendario.get(Calendar.HOUR_OF_DAY)
        val minuto = calendario.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, horaEscolhida, minutoEscolhido ->
            val textHora =
                "Hora escolhida: %02d:%02d".format(horaEscolhida, minutoEscolhido)
            println(textHora)
        }, hora, minuto, true).show()
    }
}
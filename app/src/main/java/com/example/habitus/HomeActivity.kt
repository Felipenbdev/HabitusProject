package com.example.habitus

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitus.adapters.TaskListAdapter
import com.example.habitus.entities.Tarefa
import com.example.habitus.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    var dataHora = ""
    private lateinit var taskListAdapter: TaskListAdapter
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuração dos botões e campos
        val buttonShowTasks = findViewById<Button>(R.id.showTasks)
        val buttonAddTask = findViewById<Button>(R.id.buttonAddTarefa)
        val descricaoText = findViewById<EditText>(R.id.Descricao)
        val buttonData = findViewById<Button>(R.id.Selectdata)
        val calendario = Calendar.getInstance()

        // Configuração da RecyclerView
        setupRecyclerView()

        // Listeners
        buttonData.setOnClickListener { showData(calendario) }
        buttonShowTasks.setOnClickListener { carregarTarefas() }
        buttonAddTask.setOnClickListener {
            addTask(descricaoText.text.toString(), dataHora, this)
            // Limpa os campos após adicionar
            descricaoText.text.clear()
            dataHora = ""
        }

        // Carrega as tarefas ao iniciar a atividade
        carregarTarefas()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewTasks)
        // Inicializa o adapter com uma lista vazia e a função de callback para o toggle
        taskListAdapter = TaskListAdapter(emptyList()) { tarefa ->
            toggleTaskStatus(tarefa)
        }
        recyclerView.adapter = taskListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun carregarTarefas() {
        RetrofitInstance.api.listarTarefas().enqueue(object : Callback<List<Tarefa>> {
            override fun onResponse(call: Call<List<Tarefa>>, response: Response<List<Tarefa>>) {
                if (response.isSuccessful) {
                    val tasks = response.body() ?: emptyList()
                    taskListAdapter.updateTasks(tasks) // Atualiza o adapter com as novas tarefas
                } else {
                    Toast.makeText(this@HomeActivity, "Erro ao buscar tarefas: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Tarefa>>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Falha de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun toggleTaskStatus(tarefa: Tarefa) {
        val taskId = tarefa.id ?: return
        RetrofitInstance.api.toggleTarefa(taskId).enqueue(object : Callback<Tarefa> {
            override fun onResponse(call: Call<Tarefa>, response: Response<Tarefa>) {
                if (response.isSuccessful) {
                    val updatedTask = response.body()
                    updatedTask?.let {
                        taskListAdapter.updateSingleTask(it)
                    }
                } else {
                    Toast.makeText(this@HomeActivity, "Erro ao atualizar tarefa: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Tarefa>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Falha de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun addTask(desc: String, dataHora: String, context: Context) {
        if (desc.isEmpty() || dataHora.isEmpty()) {
            Toast.makeText(context, "Descrição e data/hora são obrigatórios.", Toast.LENGTH_SHORT).show()
            return
        }

        val novaTarefa = Tarefa(descricao = desc, datahora = dataHora, ativo = true)

        RetrofitInstance.api.criarTarefa(novaTarefa).enqueue(object : Callback<Tarefa> {
            override fun onResponse(call: Call<Tarefa>, response: Response<Tarefa>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Tarefa criada", Toast.LENGTH_SHORT).show()
                    carregarTarefas() // Recarrega a lista para mostrar a nova tarefa
                } else {
                    Toast.makeText(context, "Erro ao criar tarefa: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Tarefa>, t: Throwable) {
                Toast.makeText(context, "Falha de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    fun showData(calendario: Calendar) {
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, anoEscolhido, mesEscolhido, diaEscolhido ->
            // O mês retornado é baseado em zero (0-11), então somamos 1
            val dataText = "%04d-%02d-%02d".format(anoEscolhido, mesEscolhido + 1, diaEscolhido)
            dataHora = dataText
            showHora(calendario)
        }, ano, mes, dia).show()
    }

    fun showHora(calendario: Calendar) {
        val hora = calendario.get(Calendar.HOUR_OF_DAY)
        val minuto = calendario.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, horaEscolhida, minutoEscolhido ->
            dataHora += "T%02d:%02d:00".format(horaEscolhida, minutoEscolhido)
            Toast.makeText(this, "Data e Hora selecionadas: $dataHora", Toast.LENGTH_LONG).show()
        }, hora, minuto, true).show()
    }
}
package com.example.habitus.ui.home

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import com.example.habitus.R
import androidx.lifecycle.ViewModelProvider
import com.example.habitus.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var taskListAdapter: TaskListAdapter
    private lateinit var recyclerView: RecyclerView
    var dataHora = ""

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
        // Viewmodel iniciado
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Configuração dos botões e campos
        val buttonShowTasks = findViewById<Button>(R.id.showTasks)
        val buttonAddTask = findViewById<Button>(R.id.buttonAddTarefa)
        val descricaoText = findViewById<EditText>(R.id.Descricao)
        val buttonData = findViewById<Button>(R.id.Selectdata)


        val calendario = Calendar.getInstance()

        // Configuração da RecyclerView
        setupRecyclerView()

        // Observers
        viewModel.tarefas.observe(this) { tasks ->
            taskListAdapter.updateTasks(tasks)
        }

        viewModel.erro.observe(this) { mensagem ->
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
        }

        // Listeners
        buttonData.setOnClickListener { showData(calendario) }

        buttonShowTasks.setOnClickListener {
            viewModel.carregarTarefas()
        }

        buttonAddTask.setOnClickListener {
            viewModel.criarTarefa(descricaoText.text.toString(), dataHora)
            // Limpa os campos após adicionar
            descricaoText.text.clear()
            dataHora = ""
        }

        // Carrega as tarefas ao iniciar a atividade
        viewModel.carregarTarefas()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewTasks)
        // Inicializa o adapter com uma lista vazia e a função de callback para o toggle
        taskListAdapter = TaskListAdapter(emptyList()) { tarefa ->
            viewModel.toggleTarefa(tarefa)
        }
        recyclerView.adapter = taskListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
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
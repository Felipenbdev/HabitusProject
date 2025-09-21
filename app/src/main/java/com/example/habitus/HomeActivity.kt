package com.example.habitus

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import androidx.recyclerview.widget.RecyclerView
import com.example.habitus.entities.Tarefa
import com.example.habitus.network.RetrofitInstance


class HomeActivity : AppCompatActivity() {
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
        val buttonShowTasks = findViewById<Button>(R.id.showTasks)
        val buttonAddTask = findViewById<Button>(R.id.buttonAddTarefa)

        val descricaoText = findViewById<EditText>(R.id.Descricao)
        val buttonData = findViewById<Button>(R.id.Selectdata)
        val calendario = Calendar.getInstance()

        buttonData.setOnClickListener { showData(calendario) }
        buttonShowTasks.setOnClickListener { showTasks() }
        buttonAddTask.setOnClickListener { addTask(descricaoText.text.toString(), dataHora) }
    }

    fun addTask(desc: String, dataHora: String) {
        if (desc.isEmpty() || dataHora.isEmpty()) return

        val novaTarefa = Tarefa(descricao = desc, datahora = dataHora, ativo = true)

        RetrofitInstance.api.criarTarefa(novaTarefa).enqueue(object : retrofit2.Callback<Tarefa> {
            override fun onResponse(call: retrofit2.Call<Tarefa>, response: retrofit2.Response<Tarefa>) {
                if (response.isSuccessful) {
                    println("Tarefa criada com sucesso: ${response.body()}")
                } else {
                    println("Erro ao criar tarefa: ${response.code()} - ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<Tarefa>, t: Throwable) {
                println("Falha de conexão: ${t.message}")
                t.printStackTrace()
            }
        })
    }

    fun showTasks() {

    }

    fun showData(calendario: Calendar) {
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, anoEscolhido, mesEscolhido, diaEscolhido ->
            val dataText = "%04d-%02d-%02d".format(anoEscolhido, mesEscolhido, diaEscolhido)
            dataHora = dataText
            showHora(calendario)
        }, ano, mes, dia).show()
    }

    fun showHora(calendario: Calendar) {
        val hora = calendario.get(Calendar.HOUR_OF_DAY)
        val minuto = calendario.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, horaEscolhida, minutoEscolhido ->
            dataHora += "T"
            dataHora += "%02d:%02d".format(horaEscolhida, minutoEscolhido)
            dataHora += ":00"
            println(dataHora)
        }, hora, minuto, true).show()
    }
}
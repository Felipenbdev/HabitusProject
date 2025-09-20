package com.example.habitus

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView


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

        val view = findViewById<RecyclerView>(R.id.idTasks)
    }

    fun addTask(desc: String, dataHora: String) {

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
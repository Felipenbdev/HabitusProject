package com.example.habitus

import android.widget.ProgressBar
import android.view.View
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
    var dataHora = ""
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


        val view = findViewById<TextView>(R.id.idtarefas)
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
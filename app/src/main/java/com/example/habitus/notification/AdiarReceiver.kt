package com.example.habitus.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.example.habitus.model.Tarefa
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AdiarReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {

        val id = intent.getLongExtra("id", -1)
        val datahora = intent.getStringExtra("datahora") ?: return

        if (id == -1L) return

        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val novaData =
            LocalDateTime.parse(datahora, formatter)
                .plusMinutes(10)
                .format(formatter)

        val tarefa =
            Tarefa(id = id, descricao = "", datahora = novaData, ativo = false)

        NotificationScheduler.agendar(context, tarefa)

        NotificationManagerCompat.from(context)
            .cancel(id.toInt())
    }
}

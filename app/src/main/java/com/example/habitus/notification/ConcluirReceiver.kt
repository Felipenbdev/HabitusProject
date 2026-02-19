package com.example.habitus.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkManager
import com.example.habitus.repository.TarefaRepository

class ConcluirReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val id = intent.getLongExtra("id", -1)

        if (id != -1L) {

            val repository = TarefaRepository()
            repository.toggleTarefa(id) { _, _ -> }

            WorkManager.getInstance(context)
                .cancelUniqueWork("tarefa_$id")

            NotificationManagerCompat.from(context)
                .cancel(id.toInt())
        }
    }
}

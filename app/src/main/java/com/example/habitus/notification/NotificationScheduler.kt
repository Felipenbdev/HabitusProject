package com.example.habitus.notification

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.habitus.model.Tarefa
import java.util.concurrent.TimeUnit

object NotificationScheduler {




    @RequiresApi(Build.VERSION_CODES.O)
    fun agendar(context: Context, tarefa: Tarefa) {
        val zoneId = java.time.ZoneId.systemDefault()

        val dataHora = java.time.LocalDateTime
            .parse(tarefa.datahora)
            .atZone(zoneId)

        val agora = java.time.ZonedDateTime.now(zoneId)

        val delay = java.time.Duration
            .between(agora, dataHora)
            .toMillis()

        if (delay <= 0) return


        val data = workDataOf(
            "descricao" to tarefa.descricao
        )
        val workRequest = OneTimeWorkRequestBuilder<TarefaNotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()
        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                "tarefa_${tarefa.id}",
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun atualizarAgendamento(context: Context, tarefa: Tarefa) {
        val workName = "tarefa_${tarefa.id}"

        if (tarefa.ativo == true) {
            WorkManager.getInstance(context)
                .cancelUniqueWork(workName)
        } else {
            agendar(context, tarefa)
        }
    }
}
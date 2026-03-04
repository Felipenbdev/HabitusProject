package com.example.habitus.notification

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class TarefaNotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {

        val id = inputData.getLong("id", -1)
        val descricao = inputData.getString("descricao") ?: return Result.failure()
        val datahora = inputData.getString("datahora") ?: ""

        NotificationHelper.showNotification(
            applicationContext,
            id,
            descricao,
            datahora
        )

        return Result.success()
    }
}

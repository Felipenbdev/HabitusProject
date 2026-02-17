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

        val descricao = inputData.getString("descricao") ?: return Result.failure()

        NotificationHelper.showNotification(applicationContext, descricao)

        return Result.success()
    }
}

package com.example.habitus.notification

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.habitus.R
import com.example.habitus.ui.home.HomeActivity

object NotificationHelper {

    const val CHANNEL_ID = "task_channel"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Tarefas",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(
        context: Context,
        tarefaId: Long,
        descricao: String,
        datahora: String
    ) {

        val horario = datahora.substringAfter("T").substring(0,5)

        // Abrir Home ao clicar
        val intent = Intent(context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(
            context,
            tarefaId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Botão Concluir
        val concluirIntent =
            Intent(context, ConcluirReceiver::class.java)
                .putExtra("id", tarefaId)

        val concluirPendingIntent =
            PendingIntent.getBroadcast(
                context,
                tarefaId.toInt(),
                concluirIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        // Botão Adiar
        val adiarIntent =
            Intent(context, AdiarReceiver::class.java)
                .putExtra("id", tarefaId)
                .putExtra("datahora", datahora)

        val adiarPendingIntent =
            PendingIntent.getBroadcast(
                context,
                tarefaId.toInt() + 1000,
                adiarIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Habitus")
            .setContentText("$descricao • $horario")
            .setSmallIcon(R.drawable.iconhabitus_nobackground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(0, "Concluir", concluirPendingIntent)
            .addAction(0, "Adiar 10 min", adiarPendingIntent)
            .build()

        NotificationManagerCompat.from(context)
            .notify(tarefaId.toInt(), notification)
    }
}

package com.refund.app.utils

import android.content.Context
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.refund.app.R
import com.refund.app.domain.models.Subscription

/**
 * Утилиты для работы с уведомлениями
 */
class NotificationHelper(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "refund_channel"
        const val CHANNEL_NAME = "Напоминания о подписках"
        const val CHANNEL_DESCRIPTION = "Уведомления о предстоящих списаниях"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableVibration(true)
                setShowBadge(true)
            }

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showReminder(
        subscriptionId: Long,
        subscriptionName: String,
        amount: Double,
        daysUntil: Int,
        nextBillingDate: Long
    ) {
        val intent = Intent(context, com.refund.app.presentation.main.MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("subscription_id", subscriptionId)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            subscriptionId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = "Напоминание о подписке"
        val body = "$subscriptionName спишут ${amount.toInt()} ₽ через $daysUntil дн."

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.notify(subscriptionId.toInt(), notification)
    }

    @Deprecated("Используйте новый метод showReminder с параметрами")
    fun showReminder(subscription: Subscription, daysUntil: Int) {
        showReminder(
            subscriptionId = subscription.id,
            subscriptionName = subscription.name,
            amount = subscription.amount,
            daysUntil = daysUntil,
            nextBillingDate = subscription.nextBillingDate
        )
    }

    fun cancelReminder(subscriptionId: Long) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.cancel(subscriptionId.toInt())
    }
}

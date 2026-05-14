package com.refund.app.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.refund.app.data.local.AppDatabase
import com.refund.app.data.local.entity.ReminderEntity
import com.refund.app.domain.models.Periodicity
import com.refund.app.utils.NotificationHelper
import java.util.Calendar
import java.util.concurrent.TimeUnit

/**
 * Worker для ежедневной проверки подписок и отправки напоминаний.
 * Запускается каждый день в 10:00.
 */
class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val database = AppDatabase.getDatabase(context)
    private val subscriptionDao = database.subscriptionDao()
    private val reminderDao = database.reminderDao()
    private val notificationHelper = NotificationHelper(context)

    override suspend fun doWork(): Result {
        // Проверяем, что сейчас около 10:00 (для точности можно использовать AlarmManager)
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        
        // Для MVP запускаем проверку независимо от времени, 
        // но в продакшене лучше использовать setExact с AlarmManager
        try {
            val activeSubscriptions = subscriptionDao.getActiveSubscriptions()
            val currentTime = System.currentTimeMillis()
            val threeDaysInMillis = 3 * 24 * 60 * 60 * 1000L
            val oneDayInMillis = 24 * 60 * 60 * 1000L

            for (subscription in activeSubscriptions) {
                // Пропускаем если напоминания отключены для этой подписки
                if (!subscription.remindersEnabled) continue

                val daysUntilBilling = (subscription.nextBillingDate - currentTime) / oneDayInMillis

                // Отправляем уведомление за 3 дня или за 1 день
                if (daysUntilBilling == 3L || daysUntilBilling == 1L) {
                    val reminderType = if (daysUntilBilling == 3L) "3_days" else "1_day"
                    
                    // Проверяем, не отправляли ли уже напоминание за последние 24 часа
                    val existingReminder = reminderDao.getRecentReminder(
                        subscription.id,
                        currentTime - TimeUnit.DAYS.toMillis(1)
                    )

                    if (existingReminder == null) {
                        // Создаём запись о напоминании
                        val reminder = ReminderEntity(
                            subscriptionId = subscription.id,
                            scheduledAt = currentTime,
                            type = reminderType,
                            isSent = true
                        )
                        reminderDao.insert(reminder)

                        // Отправляем уведомление
                        notificationHelper.showReminder(
                            subscriptionId = subscription.id,
                            subscriptionName = subscription.name,
                            amount = subscription.amount,
                            daysUntil = daysUntilBilling.toInt(),
                            nextBillingDate = subscription.nextBillingDate
                        )
                    }
                }
            }

            return Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "reminder_worker"
    }
}

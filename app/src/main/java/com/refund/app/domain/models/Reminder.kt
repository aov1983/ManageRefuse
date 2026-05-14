package com.refund.app.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Модель напоминания о предстоящем списании
 */
@Entity(tableName = "reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subscriptionId: Long,            // ID подписки
    val reminderType: ReminderType,      // Тип напоминания (за 3 дня, за 1 день)
    val scheduledTime: Long,             // Время отправки (timestamp)
    val sentAt: Long? = null,            // Время фактической отправки
    val isSent: Boolean = false          // Отправлено ли
)

enum class ReminderType {
    THREE_DAYS,  // За 3 дня
    ONE_DAY      // За 1 день
}

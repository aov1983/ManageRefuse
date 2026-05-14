package com.refund.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сущность напоминания для хранения в Room.
 * Соответствует FR3 (Напоминания).
 */
@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subscriptionId: Long,
    val type: String, // '3days', '1day'
    val scheduledTime: Long, // Timestamp запланированного времени
    val isSent: Boolean = false,
    val sentAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)

package com.refund.app.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Модель подписки в базе данных
 */
@Entity(tableName = "subscriptions")
data class Subscription(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val name: String,                    // Название сервиса
    val amount: Double,                  // Сумма списания
    val startDate: Long,                 // Дата первого списания (timestamp)
    val periodicity: Periodicity,        // Периодичность списания
    val iconResId: Int,                  // ID иконки в ресурсах
    val nextBillingDate: Long,           // Дата следующего списания
    val isActive: Boolean = true,        // Активна ли подписка
    val isDeleted: Boolean = false,      // Удалена ли (soft delete)
    val cancelledAt: Long? = null,       // Дата отмены
    val remindersEnabled: Boolean = true,// Включены ли напоминания для этой подписки
    val createdAt: Long = System.currentTimeMillis()
)

enum class Periodicity {
    WEEKLY,      // Еженедельно
    MONTHLY,     // Ежемесячно
    YEARLY,      // Ежегодно
    ONE_TIME     // Разово
}

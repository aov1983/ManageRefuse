package com.refund.app.domain.models

/**
 * Модель подписки (Domain layer).
 * Не содержит аннотаций Room, используется только в бизнес-логике.
 */
data class Subscription(
    val id: Long = 0,
    val name: String,                    // Название сервиса
    val amount: Double,                  // Сумма списания
    val currency: String = "RUB",        // Валюта
    val startDate: Long,                 // Дата первого списания (timestamp)
    val periodicity: Periodicity,        // Периодичность списания
    val nextBillingDate: Long,           // Дата следующего списания
    val isActive: Boolean = true,        // Активна ли подписка
    val isDeleted: Boolean = false,      // Удалена ли (soft delete)
    val cancelledAt: Long? = null,       // Дата отмены
    val remindersEnabled: Boolean = true,// Включены ли напоминания для этой подписки
    val iconResId: Int? = null,          // ID иконки в ресурсах
    val createdAt: Long = System.currentTimeMillis()
)

enum class Periodicity {
    WEEKLY,      // Еженедельно
    MONTHLY,     // Ежемесячно
    YEARLY,      // Ежегодно
    ONE_TIME     // Разово
}

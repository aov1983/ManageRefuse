package com.refund.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сущность подписки для хранения в Room.
 * Соответствует FR2 (Управление подписками).
 */
@Entity(tableName = "subscriptions")
data class SubscriptionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val amount: Double,
    val currency: String = "RUB",
    val startDate: Long, // Timestamp первого списания
    val nextBillingDate: Long, // Timestamp следующего списания
    val periodicity: String, // MONTHLY, YEARLY, WEEKLY, ONE_TIME
    val isActive: Boolean = true,
    val isDeleted: Boolean = false, // Soft delete
    val cancelledAt: Long? = null, // Timestamp отмены
    val remindersEnabled: Boolean = true,
    val iconResId: Int? = null, // ID ресурса иконки
    val createdAt: Long = System.currentTimeMillis()
)

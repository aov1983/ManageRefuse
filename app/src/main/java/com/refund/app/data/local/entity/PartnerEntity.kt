package com.refund.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сущность партнёра для хранения в Room.
 * Соответствует FR6 (Партнёрские предложения).
 */
@Entity(tableName = "partners")
data class PartnerEntity(
    @PrimaryKey val id: String,
    val name: String,
    val urlTemplate: String, // Шаблон URL с параметрами {user_id}, {subscription_id}
    val matchServiceName: String, // Ключевое слово для сопоставления с подпиской
    val iconResId: Int? = null,
    val isActive: Boolean = true,
    val lastUpdated: Long = System.currentTimeMillis()
)

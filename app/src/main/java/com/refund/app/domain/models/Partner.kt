package com.refund.app.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Модель партнёра для альтернативных предложений
 */
@Entity(tableName = "partners")
data class Partner(
    @PrimaryKey
    val id: String,
    val name: String,                    // Название партнёра
    val urlTemplate: String,             // Шаблон URL с параметрами
    val matchServiceName: String,        // Название сервиса для сопоставления
    val iconResId: Int = 0,              // ID иконки
    val isActive: Boolean = true,        // Активен ли партнёр
    val lastUpdated: Long = System.currentTimeMillis()
)

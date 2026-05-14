package com.refund.app.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Модель для логирования кликов по партнёрским ссылкам
 */
@Entity(tableName = "partner_clicks")
data class PartnerClick(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val partnerId: String,               // ID партнёра
    val subscriptionId: Long,            // ID подписки
    val timestamp: Long = System.currentTimeMillis()
)

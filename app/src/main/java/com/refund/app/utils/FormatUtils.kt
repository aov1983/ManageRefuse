package com.refund.app.utils

import java.text.NumberFormat
import java.util.Locale

/**
 * Утилиты для форматирования данных
 */
object FormatUtils {

    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("ru", "RU")).apply {
        maximumFractionDigits = 0
    }

    fun formatCurrency(amount: Double): String {
        return currencyFormat.format(amount)
    }

    fun formatDate(timestamp: Long): String {
        val sdf = java.text.SimpleDateFormat("dd.MM.yyyy", Locale("ru"))
        return sdf.format(java.util.Date(timestamp))
    }

    fun formatDaysUntil(timestamp: Long): Int {
        val now = System.currentTimeMillis()
        val diff = timestamp - now
        return (diff / (24 * 60 * 60 * 1000)).toInt()
    }

    fun formatPercentage(value: Double): String {
        return String.format(Locale("ru"), "%.1f%%", value)
    }
}

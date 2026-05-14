package com.refund.app.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.refund.app.data.local.AppDatabase
import com.refund.app.data.local.entity.PartnerEntity
import kotlinx.coroutines.flow.first

/**
 * Worker для ежедневной синхронизации списка партнёров.
 * В MVP используется локальный JSON, но структура готова для подключения API.
 */
class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val database = AppDatabase.getDatabase(context)
    private val partnerDao = database.partnerDao()

    override suspend fun doWork(): Result {
        return try {
            // В MVP загружаем партнёров из локального assets
            // В будущем здесь будет вызов Retrofit API
            val partnersJson = applicationContext.assets.open("partners.json").use { inputStream ->
                inputStream.bufferedReader().readText()
            }

            // Парсим JSON (в MVP простой пример, в продакшене использовать Moshi/Gson)
            val partners = parsePartnersFromJson(partnersJson)

            // Сохраняем в базу данных
            partnerDao.insertAll(partners)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            // При отсутствии интернета используем кэш из БД
            Result.retry()
        }
    }

    /**
     * Простой парсер для MVP. В продакшене использовать Moshi/Gson.
     */
    private fun parsePartnersFromJson(json: String): List<PartnerEntity> {
        // Заглушка для MVP - возвращаем пустой список
        // В реальной реализации здесь будет парсинг JSON
        return emptyList()
    }

    companion object {
        const val WORK_NAME = "sync_worker"
    }
}

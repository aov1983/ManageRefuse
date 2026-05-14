package com.refund.app.domain.repositories

import com.refund.app.domain.models.Subscription
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория для работы с подписками
 */
interface ISubscriptionRepository {

    fun getActiveSubscriptions(): Flow<List<Subscription>>

    fun getUpcomingSubscriptions(days: Int = 7): Flow<List<Subscription>>

    fun getHistorySubscriptions(): Flow<List<Subscription>>

    suspend fun getSubscriptionById(id: Long): Subscription?

    suspend fun addSubscription(subscription: Subscription): Long

    suspend fun updateSubscription(subscription: Subscription)

    suspend fun deleteSubscription(subscription: Subscription)

    suspend fun softDeleteSubscription(id: Long)

    suspend fun cancelSubscription(id: Long)

    suspend fun restoreSubscription(id: Long)

    suspend fun getAllSubscriptions(): List<Subscription>
}

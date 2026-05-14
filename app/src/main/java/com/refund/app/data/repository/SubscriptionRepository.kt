package com.refund.app.data.repository

import com.refund.app.data.local.SubscriptionDao
import com.refund.app.data.mapper.SubscriptionMapper
import com.refund.app.domain.models.Subscription
import com.refund.app.domain.repositories.ISubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubscriptionRepository @Inject constructor(
    private val subscriptionDao: SubscriptionDao
) : ISubscriptionRepository {

    override fun getActiveSubscriptions(): Flow<List<Subscription>> {
        return subscriptionDao.getActiveSubscriptions().map { SubscriptionMapper.toDomainList(it) }
    }

    override fun getUpcomingSubscriptions(days: Int): Flow<List<Subscription>> {
        val daysInMillis = days * 24 * 60 * 60 * 1000L
        return subscriptionDao.getUpcomingSubscriptions(System.currentTimeMillis(), daysInMillis)
            .map { SubscriptionMapper.toDomainList(it) }
    }

    override fun getHistorySubscriptions(): Flow<List<Subscription>> {
        return subscriptionDao.getHistorySubscriptions().map { SubscriptionMapper.toDomainList(it) }
    }

    override suspend fun getSubscriptionById(id: Long): Subscription? {
        return subscriptionDao.getSubscriptionById(id)?.let { SubscriptionMapper.toDomain(it) }
    }

    override suspend fun addSubscription(subscription: Subscription): Long {
        return subscriptionDao.insert(SubscriptionMapper.toEntity(subscription))
    }

    override suspend fun updateSubscription(subscription: Subscription) {
        subscriptionDao.update(SubscriptionMapper.toEntity(subscription))
    }

    override suspend fun deleteSubscription(subscription: Subscription) {
        subscriptionDao.delete(SubscriptionMapper.toEntity(subscription))
    }

    override suspend fun softDeleteSubscription(id: Long) {
        subscriptionDao.softDelete(id)
    }

    override suspend fun cancelSubscription(id: Long) {
        subscriptionDao.cancelSubscription(id)
    }

    override suspend fun restoreSubscription(id: Long) {
        // Вычисляем следующую дату списания от текущей даты
        val subscription = subscriptionDao.getSubscriptionById(id) ?: return
        val nextDate = calculateNextBillingDate(
            startDate = System.currentTimeMillis(),
            periodicity = subscription.periodicity
        )
        subscriptionDao.restoreSubscription(id, nextDate)
    }

    override suspend fun getAllSubscriptions(): List<Subscription> {
        return subscriptionDao.getAllSubscriptions().map { SubscriptionMapper.toDomain(it) }
    }

    /**
     * Вычисляет следующую дату списания на основе периодичности
     */
    private fun calculateNextBillingDate(startDate: Long, periodicity: com.refund.app.domain.models.Periodicity): Long {
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = startDate
        }

        return when (periodicity) {
            com.refund.app.domain.models.Periodicity.WEEKLY -> {
                calendar.add(java.util.Calendar.WEEK_OF_YEAR, 1)
                calendar.timeInMillis
            }
            com.refund.app.domain.models.Periodicity.MONTHLY -> {
                calendar.add(java.util.Calendar.MONTH, 1)
                calendar.timeInMillis
            }
            com.refund.app.domain.models.Periodicity.YEARLY -> {
                calendar.add(java.util.Calendar.YEAR, 1)
                calendar.timeInMillis
            }
            com.refund.app.domain.models.Periodicity.ONE_TIME -> {
                startDate
            }
        }
    }
}

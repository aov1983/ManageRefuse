package com.refund.app.data.local

import androidx.room.*
import com.refund.app.data.local.entity.SubscriptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriptionDao {

    @Query("SELECT * FROM subscriptions WHERE isDeleted = 0 AND isActive = 1 ORDER BY nextBillingDate ASC")
    fun getActiveSubscriptions(): Flow<List<SubscriptionEntity>>

    @Query("SELECT * FROM subscriptions WHERE isDeleted = 0 AND isActive = 1 AND (nextBillingDate - :currentTime) <= :daysInMillis ORDER BY nextBillingDate ASC")
    fun getUpcomingSubscriptions(currentTime: Long, daysInMillis: Long): Flow<List<SubscriptionEntity>>

    @Query("SELECT * FROM subscriptions WHERE isDeleted = 1 OR isActive = 0 ORDER BY cancelledAt DESC, createdAt DESC")
    fun getHistorySubscriptions(): Flow<List<SubscriptionEntity>>

    @Query("SELECT * FROM subscriptions WHERE id = :id")
    suspend fun getSubscriptionById(id: Long): SubscriptionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subscription: SubscriptionEntity): Long

    @Update
    suspend fun update(subscription: SubscriptionEntity)

    @Delete
    suspend fun delete(subscription: SubscriptionEntity)

    @Query("UPDATE subscriptions SET isDeleted = 1, cancelledAt = :timestamp WHERE id = :id")
    suspend fun softDelete(id: Long, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE subscriptions SET isActive = 0, cancelledAt = :timestamp WHERE id = :id")
    suspend fun cancelSubscription(id: Long, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE subscriptions SET isActive = 1, cancelledAt = NULL, nextBillingDate = :nextDate WHERE id = :id")
    suspend fun restoreSubscription(id: Long, nextDate: Long)

    @Query("SELECT * FROM subscriptions WHERE isDeleted = 0 AND isActive = 1")
    suspend fun getAllActiveSubscriptions(): List<SubscriptionEntity>

    @Query("SELECT * FROM subscriptions")
    suspend fun getAllSubscriptions(): List<SubscriptionEntity>
}

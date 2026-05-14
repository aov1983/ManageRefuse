package com.refund.app.data.local

import androidx.room.*
import com.refund.app.domain.models.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminders WHERE subscriptionId = :subscriptionId ORDER BY scheduledTime DESC")
    fun getRemindersForSubscription(subscriptionId: Long): Flow<List<Reminder>>

    @Query("SELECT * FROM reminders WHERE isSent = 0 AND scheduledTime <= :currentTime ORDER BY scheduledTime ASC")
    suspend fun getPendingReminders(currentTime: Long): List<Reminder>

    @Query("SELECT * FROM reminders WHERE id = :id")
    suspend fun getReminderById(id: Long): Reminder?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: Reminder): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reminders: List<Reminder>)

    @Update
    suspend fun update(reminder: Reminder)

    @Delete
    suspend fun delete(reminder: Reminder)

    @Query("DELETE FROM reminders WHERE subscriptionId = :subscriptionId")
    suspend fun deleteAllForSubscription(subscriptionId: Long)

    @Query("UPDATE reminders SET isSent = 1, sentAt = :timestamp WHERE id = :id")
    suspend fun markAsSent(id: Long, timestamp: Long = System.currentTimeMillis())

    @Query("DELETE FROM reminders WHERE scheduledTime < :oldTimestamp")
    suspend fun deleteOldReminders(oldTimestamp: Long)

    @Query("SELECT COUNT(*) FROM reminders WHERE subscriptionId = :subscriptionId AND reminderType = :type AND sentAt > :todayMillis")
    suspend fun hasReminderSentToday(subscriptionId: Long, type: Int, todayMillis: Long): Int
}

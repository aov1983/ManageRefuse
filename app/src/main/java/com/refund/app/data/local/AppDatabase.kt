package com.refund.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.refund.app.data.local.entity.SubscriptionEntity
import com.refund.app.data.local.entity.PartnerEntity
import com.refund.app.data.local.entity.ReminderEntity
import com.refund.app.domain.models.PartnerClick

@Database(
    entities = [
        SubscriptionEntity::class,
        PartnerEntity::class,
        ReminderEntity::class,
        PartnerClick::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun partnerDao(): PartnerDao
    abstract fun reminderDao(): ReminderDao
    abstract fun partnerClickDao(): PartnerClickDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "refund_database"
                )
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

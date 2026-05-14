package com.refund.app.di

import android.content.Context
import androidx.room.Room
import com.refund.app.data.local.AppDatabase
import com.refund.app.data.local.SubscriptionDao
import com.refund.app.data.local.PartnerDao
import com.refund.app.data.local.ReminderDao
import com.refund.app.data.local.PartnerClickDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideSubscriptionDao(database: AppDatabase): SubscriptionDao {
        return database.subscriptionDao()
    }

    @Provides
    @Singleton
    fun providePartnerDao(database: AppDatabase): PartnerDao {
        return database.partnerDao()
    }

    @Provides
    @Singleton
    fun provideReminderDao(database: AppDatabase): ReminderDao {
        return database.reminderDao()
    }

    @Provides
    @Singleton
    fun providePartnerClickDao(database: AppDatabase): PartnerClickDao {
        return database.partnerClickDao()
    }
}

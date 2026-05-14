package com.refund.app.di

import com.refund.app.data.local.SubscriptionDao
import com.refund.app.data.repository.SubscriptionRepository
import com.refund.app.domain.repositories.ISubscriptionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSubscriptionRepository(
        subscriptionDao: SubscriptionDao
    ): ISubscriptionRepository {
        return SubscriptionRepository(subscriptionDao)
    }
}

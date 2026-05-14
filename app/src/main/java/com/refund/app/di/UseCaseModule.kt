package com.refund.app.di

import com.refund.app.domain.repositories.ISubscriptionRepository
import com.refund.app.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideAddSubscriptionUseCase(repository: ISubscriptionRepository): AddSubscriptionUseCase {
        return AddSubscriptionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateSubscriptionUseCase(repository: ISubscriptionRepository): UpdateSubscriptionUseCase {
        return UpdateSubscriptionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteSubscriptionUseCase(repository: ISubscriptionRepository): DeleteSubscriptionUseCase {
        return DeleteSubscriptionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCancelSubscriptionUseCase(repository: ISubscriptionRepository): CancelSubscriptionUseCase {
        return CancelSubscriptionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetStatisticsUseCase(repository: ISubscriptionRepository): GetStatisticsUseCase {
        return GetStatisticsUseCase(repository)
    }
}

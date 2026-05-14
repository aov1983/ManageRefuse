package com.refund.app.domain.usecases

import com.refund.app.domain.models.Subscription
import com.refund.app.domain.repositories.ISubscriptionRepository
import javax.inject.Inject

/**
 * UseCase для обновления подписки
 */
class UpdateSubscriptionUseCase @Inject constructor(
    private val repository: ISubscriptionRepository
) {
    suspend operator fun invoke(subscription: Subscription) {
        repository.updateSubscription(subscription)
    }
}

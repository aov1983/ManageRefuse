package com.refund.app.domain.usecases

import com.refund.app.domain.repositories.ISubscriptionRepository
import javax.inject.Inject

/**
 * UseCase для отмены подписки
 */
class CancelSubscriptionUseCase @Inject constructor(
    private val repository: ISubscriptionRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.cancelSubscription(id)
    }
}

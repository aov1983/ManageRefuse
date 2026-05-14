package com.refund.app.presentation.subscriptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.refund.app.domain.models.Subscription
import com.refund.app.domain.repositories.ISubscriptionRepository
import com.refund.app.domain.usecases.AddSubscriptionUseCase
import com.refund.app.domain.usecases.CancelSubscriptionUseCase
import com.refund.app.domain.usecases.DeleteSubscriptionUseCase
import com.refund.app.domain.usecases.UpdateSubscriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SubscriptionsViewModel @Inject constructor(
    private val repository: ISubscriptionRepository,
    private val addSubscriptionUseCase: AddSubscriptionUseCase,
    private val updateSubscriptionUseCase: UpdateSubscriptionUseCase,
    private val deleteSubscriptionUseCase: DeleteSubscriptionUseCase,
    private val cancelSubscriptionUseCase: CancelSubscriptionUseCase
) : ViewModel() {

    val activeSubscriptions: Flow<List<Subscription>> = repository.getActiveSubscriptions()
    val upcomingSubscriptions: Flow<List<Subscription>> = repository.getUpcomingSubscriptions(7)
    val historySubscriptions: Flow<List<Subscription>> = repository.getHistorySubscriptions()

    fun addSubscription(subscription: Subscription) {
        addSubscriptionUseCase(subscription)
    }

    fun updateSubscription(subscription: Subscription) {
        updateSubscriptionUseCase(subscription)
    }

    fun deleteSubscription(subscription: Subscription) {
        deleteSubscriptionUseCase(subscription)
    }

    fun softDeleteSubscription(id: Long) {
        repository.softDeleteSubscription(id)
    }

    fun cancelSubscription(id: Long) {
        cancelSubscriptionUseCase(id)
    }

    fun restoreSubscription(id: Long) {
        repository.restoreSubscription(id)
    }
}

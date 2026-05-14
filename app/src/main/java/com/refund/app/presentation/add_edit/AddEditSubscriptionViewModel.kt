package com.refund.app.presentation.add_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.refund.app.domain.models.Periodicity
import com.refund.app.domain.models.Subscription
import com.refund.app.domain.usecases.AddSubscriptionUseCase
import com.refund.app.domain.usecases.UpdateSubscriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditSubscriptionViewModel @Inject constructor(
    private val addSubscriptionUseCase: AddSubscriptionUseCase,
    private val updateSubscriptionUseCase: UpdateSubscriptionUseCase
) : ViewModel() {

    var subscriptionId: Long = -1L
    var name: String = ""
    var amount: Double = 0.0
    var startDate: Long = System.currentTimeMillis()
    var periodicity: Periodicity = Periodicity.MONTHLY
    var iconResId: Int = 0
    var remindersEnabled: Boolean = true

    fun setSubscriptionData(subscription: Subscription) {
        subscriptionId = subscription.id
        name = subscription.name
        amount = subscription.amount
        startDate = subscription.startDate
        periodicity = subscription.periodicity
        iconResId = subscription.iconResId
        remindersEnabled = subscription.remindersEnabled
    }

    fun saveSubscription() {
        if (subscriptionId == -1L) {
            // Добавление новой подписки
            val newSubscription = Subscription(
                id = 0,
                name = name,
                amount = amount,
                currency = "₽",
                startDate = startDate,
                nextBillingDate = calculateNextBillingDate(startDate, periodicity),
                periodicity = periodicity,
                isActive = true,
                isDeleted = false,
                cancelledAt = null,
                remindersEnabled = remindersEnabled,
                iconResId = iconResId,
                createdAt = System.currentTimeMillis()
            )
            addSubscriptionUseCase(newSubscription)
        } else {
            // Обновление существующей подписки
            val updatedSubscription = Subscription(
                id = subscriptionId,
                name = name,
                amount = amount,
                currency = "₽",
                startDate = startDate,
                nextBillingDate = calculateNextBillingDate(startDate, periodicity),
                periodicity = periodicity,
                isActive = true,
                isDeleted = false,
                cancelledAt = null,
                remindersEnabled = remindersEnabled,
                iconResId = iconResId,
                createdAt = System.currentTimeMillis() // сохраняем оригинальную дату создания
            )
            updateSubscriptionUseCase(updatedSubscription)
        }
    }

    private fun calculateNextBillingDate(startDate: Long, periodicity: Periodicity): Long {
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = startDate
        }

        return when (periodicity) {
            Periodicity.WEEKLY -> {
                calendar.add(java.util.Calendar.WEEK_OF_YEAR, 1)
                calendar.timeInMillis
            }
            Periodicity.MONTHLY -> {
                calendar.add(java.util.Calendar.MONTH, 1)
                calendar.timeInMillis
            }
            Periodicity.YEARLY -> {
                calendar.add(java.util.Calendar.YEAR, 1)
                calendar.timeInMillis
            }
            Periodicity.ONE_TIME -> {
                startDate
            }
        }
    }
}

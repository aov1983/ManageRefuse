package com.refund.app.domain.usecases

import com.refund.app.domain.models.Periodicity
import com.refund.app.domain.models.Subscription
import com.refund.app.domain.repositories.ISubscriptionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase для получения статистики по подпискам
 */
data class SubscriptionStatistics(
    val monthlySpend: Double,      // Траты в месяц
    val totalSaved: Double,        // Всего сэкономлено
    val activeCount: Int,          // Количество активных подписок
    val savingsPercentage: Double  // Процент сэкономленного
)

class GetStatisticsUseCase @Inject constructor(
    private val repository: ISubscriptionRepository
) {
    suspend operator fun invoke(): SubscriptionStatistics {
        val subscriptions = repository.getAllSubscriptions()
        
        // Активные подписки
        val activeSubscriptions = subscriptions.filter { it.isActive && !it.isDeleted }
        
        // Отменённые (но не удалённые) - для подсчёта экономии
        val cancelledSubscriptions = subscriptions.filter { !it.isActive && !it.isDeleted }
        
        // Расчёт месячных трат
        var monthlySpend = 0.0
        activeSubscriptions.forEach { subscription ->
            monthlySpend += when (subscription.periodicity) {
                Periodicity.WEEKLY -> subscription.amount * 4.33
                Periodicity.MONTHLY -> subscription.amount
                Periodicity.YEARLY -> subscription.amount / 12.0
                Periodicity.ONE_TIME -> 0.0 // Разовые не учитываем в месячных тратах
            }
        }
        
        // Расчёт экономии
        val totalSaved = cancelledSubscriptions.sumOf { it.amount }
        
        // Процент экономии
        val savingsPercentage = if (monthlySpend + totalSaved > 0) {
            (totalSaved / (monthlySpend + totalSaved)) * 100
        } else {
            0.0
        }
        
        return SubscriptionStatistics(
            monthlySpend = monthlySpend,
            totalSaved = totalSaved,
            activeCount = activeSubscriptions.size,
            savingsPercentage = savingsPercentage
        )
    }
}

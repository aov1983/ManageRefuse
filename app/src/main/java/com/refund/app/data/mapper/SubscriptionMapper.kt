package com.refund.app.data.mapper

import com.refund.app.data.local.entity.SubscriptionEntity
import com.refund.app.domain.models.Subscription
import com.refund.app.domain.models.Periodicity

/**
 * Маппер для конвертации между SubscriptionEntity и Subscription.
 */
object SubscriptionMapper {

    fun toDomain(entity: SubscriptionEntity): Subscription {
        return Subscription(
            id = entity.id,
            name = entity.name,
            amount = entity.amount,
            currency = entity.currency,
            startDate = entity.startDate,
            nextBillingDate = entity.nextBillingDate,
            periodicity = Periodicity.valueOf(entity.periodicity),
            isActive = entity.isActive,
            isDeleted = entity.isDeleted,
            cancelledAt = entity.cancelledAt,
            remindersEnabled = entity.remindersEnabled,
            iconResId = entity.iconResId,
            createdAt = entity.createdAt
        )
    }

    fun toEntity(domain: Subscription): SubscriptionEntity {
        return SubscriptionEntity(
            id = domain.id,
            name = domain.name,
            amount = domain.amount,
            currency = domain.currency,
            startDate = domain.startDate,
            nextBillingDate = domain.nextBillingDate,
            periodicity = domain.periodicity.name,
            isActive = domain.isActive,
            isDeleted = domain.isDeleted,
            cancelledAt = domain.cancelledAt,
            remindersEnabled = domain.remindersEnabled,
            iconResId = domain.iconResId,
            createdAt = domain.createdAt
        )
    }

    fun toDomainList(entities: List<SubscriptionEntity>): List<Subscription> {
        return entities.map { toDomain(it) }
    }

    fun toEntityList(domains: List<Subscription>): List<SubscriptionEntity> {
        return domains.map { toEntity(it) }
    }
}

package com.puzzle.industries.data.services

import com.puzzle.industries.domain.models.user.Subscription
import com.puzzle.industries.domain.services.SubscriptionService
import kotlinx.coroutines.flow.Flow

internal class SubscriptionServiceImpl : SubscriptionService {
    override fun getSubscriptionState(): Flow<Subscription> {
        TODO("Not yet implemented")
    }

    override fun updateSubscription(subscription: Subscription) {
        TODO("Not yet implemented")
    }
}
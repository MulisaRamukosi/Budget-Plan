package com.puzzle.industries.domain.services

import com.puzzle.industries.domain.models.user.Subscription
import kotlinx.coroutines.flow.Flow

interface SubscriptionService {

    fun getSubscriptionState(): Flow<Subscription>
    fun updateSubscription(subscription: Subscription)

}
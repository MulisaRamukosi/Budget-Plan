package com.puzzle.industries.budgetplan

import android.app.Application
import com.puzzle.industries.data.notification.NotificationChannelService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BudgetPlan : Application() {

    @Inject lateinit var notificationChannelService: NotificationChannelService

    override fun onCreate() {
        super.onCreate()
        notificationChannelService.createNotificationChannels()
    }
}
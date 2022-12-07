package com.puzzle.industries.data.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.puzzle.industries.data.notification.handler.PaymentReminderNotificationHandler
import com.puzzle.industries.data.util.config.ReminderConfig
import com.puzzle.industries.domain.services.CountryCurrencyService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class ReminderBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var countryCurrencyService: CountryCurrencyService

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.let {
                val id = intent.getIntExtra(ReminderConfig.KEY_ID, 0)
                val title = intent.getStringExtra(ReminderConfig.KEY_TITLE) ?: ""
                val amount = intent.getDoubleExtra(ReminderConfig.KEY_AMOUNT, 0.0)

                PaymentReminderNotificationHandler(
                    context = context,
                    countryCurrencyService = countryCurrencyService
                ).notify(
                    id = id,
                    title = title,
                    amount = amount
                )
            }
        }
    }
}
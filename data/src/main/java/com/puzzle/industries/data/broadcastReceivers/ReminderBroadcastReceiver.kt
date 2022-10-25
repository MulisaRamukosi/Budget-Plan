package com.puzzle.industries.data.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.puzzle.industries.data.notification.handler.PaymentReminderNotificationHandler
import com.puzzle.industries.data.util.config.ReminderConfig

internal class ReminderBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.let {
                val id = intent.getIntExtra(ReminderConfig.KEY_ID, 0)
                val title = intent.getStringExtra(ReminderConfig.KEY_TITLE) ?: ""
                val amount = intent.getDoubleExtra(ReminderConfig.KEY_AMOUNT, 0.0)

                PaymentReminderNotificationHandler(context).notify(
                    id = id,
                    title = title,
                    amount = amount
                )
            }
        }
    }
}
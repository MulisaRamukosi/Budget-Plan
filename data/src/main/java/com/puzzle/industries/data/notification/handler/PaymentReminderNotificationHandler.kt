package com.puzzle.industries.data.notification.handler

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.puzzle.industries.data.R
import com.puzzle.industries.data.notification.NotificationHandler
import com.puzzle.industries.data.notification.utils.NotificationChannelID
import com.puzzle.industries.data.storage.datastore.CountryCurrencyDataStoreImpl
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

internal class PaymentReminderNotificationHandler(private val context: Context) :
    NotificationHandler(context = context),
    CountryCurrencyDataStore by CountryCurrencyDataStoreImpl(context = context) {

    override val notificationChannelID: NotificationChannelID
        get() = NotificationChannelID.PAYMENT_REMINDER

    //TODO: if we want to open an activity, we can use deep link
    override val pendingIntent: PendingIntent
        get() = PendingIntent.getActivity(context, 0, Intent(), PendingIntent.FLAG_IMMUTABLE)


    fun notify(id: Int, title: String, amount: Double) = runBlocking {
        val notificationTitle  = context.getString(R.string.format_payment_reminder, title)
        val currencySymbol = getCurrencySymbol().first()
        val notificationContent = context.getString(R.string.format_payment_reminder_desc,
        currencySymbol, amount, title)

        triggerNotification(
            id = id,
            title = notificationTitle,
            content = notificationContent
        )

    }

}
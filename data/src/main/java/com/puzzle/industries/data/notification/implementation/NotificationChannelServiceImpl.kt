package com.puzzle.industries.data.notification.implementation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.puzzle.industries.data.R
import com.puzzle.industries.data.notification.NotificationChannelService
import com.puzzle.industries.data.notification.models.Channel
import com.puzzle.industries.data.notification.utils.NotificationChannelID

internal class NotificationChannelServiceImpl constructor(context: Context) : NotificationChannelService {

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val channels: List<Channel> = listOf(
        Channel(
            id = NotificationChannelID.PAYMENT_REMINDER,
            name = context.getString(R.string.channel_payment_reminders),
            description = context.getString(R.string.channel_payment_reminders_desc)
        )
    )

    override fun createNotificationChannels() {
        channels.forEach { notificationChannel ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    notificationChannel.id.name,
                    notificationChannel.name,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = notificationChannel.description
                }
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}
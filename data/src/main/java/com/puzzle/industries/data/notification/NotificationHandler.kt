package com.puzzle.industries.data.notification

import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.puzzle.industries.data.R
import com.puzzle.industries.data.notification.utils.NotificationChannelID

abstract class NotificationHandler constructor(private val context: Context) {

    abstract val notificationChannelID: NotificationChannelID
    abstract val pendingIntent: PendingIntent

    protected fun triggerNotification(id: Int, title: String, content: String) {
        val builder = NotificationCompat.Builder(context, notificationChannelID.name)
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(id, builder.build())
        }
    }
}
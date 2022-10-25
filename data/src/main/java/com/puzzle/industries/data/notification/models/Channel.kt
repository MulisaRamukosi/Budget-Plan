package com.puzzle.industries.data.notification.models

import com.puzzle.industries.data.notification.utils.NotificationChannelID

data class Channel(
    val id: NotificationChannelID,
    val name: String,
    val description: String
)

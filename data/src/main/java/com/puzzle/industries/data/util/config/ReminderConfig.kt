package com.puzzle.industries.data.util.config

import com.puzzle.industries.domain.constants.FrequencyType

object ReminderConfig {
    const val HOUR_OF_DAY = 12
    const val MINUTE = 0
    const val KEY_ID = "key_id"
    const val KEY_TITLE = "key_title"
    const val KEY_AMOUNT = "key_amount"
    const val KEY_SELECTED_IDS = "key_reminder_ids"

    val SUPPORTED_FREQUENCY_TYPE_FOR_ALARM = listOf(
        FrequencyType.ONCE_OFF,
        FrequencyType.DAILY,
        FrequencyType.WEEKLY
    )
}
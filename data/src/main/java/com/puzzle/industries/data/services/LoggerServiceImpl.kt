package com.puzzle.industries.data.services

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.puzzle.industries.domain.services.LoggerService

class LoggerServiceImpl : LoggerService {
    private val firebaseCrashlytics = FirebaseCrashlytics.getInstance()

    override fun logException(exception: java.lang.Exception) {
            firebaseCrashlytics.recordException(exception)
    }

    override fun logMessage(message: String) {
        firebaseCrashlytics.log(message)
    }
}
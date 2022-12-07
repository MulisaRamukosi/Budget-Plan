package com.puzzle.industries.domain.services

interface LoggerService {
    fun logException(exception: java.lang.Exception)
    fun logMessage(message: String)
}
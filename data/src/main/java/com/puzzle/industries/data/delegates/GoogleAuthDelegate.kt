package com.puzzle.industries.data.delegates

import com.puzzle.industries.data.callbacks.AuthCallback

interface GoogleAuthDelegate {
    fun linkAuthCallbackToGoogle(authCallback: AuthCallback)
    suspend fun authThroughGoogle()
}
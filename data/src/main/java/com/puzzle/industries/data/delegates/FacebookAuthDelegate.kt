package com.puzzle.industries.data.delegates

import com.puzzle.industries.data.callbacks.AuthCallback

interface FacebookAuthDelegate {
    fun linkAuthCallbackToFacebook(authCallback: AuthCallback)
    suspend fun authThroughFacebook()
}
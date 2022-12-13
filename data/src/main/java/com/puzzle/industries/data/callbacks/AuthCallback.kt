package com.puzzle.industries.data.callbacks

import com.puzzle.industries.domain.constants.AuthType

interface AuthCallback {

    fun onReceiveToken(token: String, authType: AuthType)
    fun onAuthCancelled()
    fun onAuthFailed(ex: Exception)
}
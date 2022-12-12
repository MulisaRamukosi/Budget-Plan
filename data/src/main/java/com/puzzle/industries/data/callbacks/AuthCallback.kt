package com.puzzle.industries.data.callbacks

interface AuthCallback {

    fun onReceiveToken(idToken: String?)
    fun onAuthCancelled()
    fun onAuthFailed(ex: Exception)
}
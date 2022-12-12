package com.puzzle.industries.data.delegates

import com.puzzle.industries.data.util.AuthFailCause
import com.puzzle.industries.domain.models.user.AuthResponse
import kotlinx.coroutines.flow.MutableSharedFlow

interface AuthResponseDelegate {
    fun authResponseProducer(): MutableSharedFlow<AuthResponse>
    fun emitAuthSuccess(isAccountLessAuth: Boolean = false)
    fun emitAuthFailed(cause: AuthFailCause, exception: Exception? = null)
    fun emitAuthCancelled()
    fun emitAuthSuccessButRequireEmailVerification()
    fun emitForgotPasswordSuccessResponse()
    fun emitForgotPasswordFailedResponse()
}
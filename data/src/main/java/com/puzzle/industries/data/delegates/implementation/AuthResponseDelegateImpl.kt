package com.puzzle.industries.data.delegates.implementation

import android.content.Context
import com.puzzle.industries.data.R
import com.puzzle.industries.data.delegates.AuthResponseDelegate
import com.puzzle.industries.data.delegates.CoroutineDelegate
import com.puzzle.industries.data.util.AuthFailCause
import com.puzzle.industries.domain.models.user.AuthResponse
import com.puzzle.industries.domain.services.LoggerService
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.MutableSharedFlow

class AuthResponseDelegateImpl(
    @ActivityContext private val context: Context,
    private val loggerService: LoggerService
) : AuthResponseDelegate,
    CoroutineDelegate by CoroutineDelegateImpl(){

    private val _authResponse: MutableSharedFlow<AuthResponse> = MutableSharedFlow()

    override fun authResponseProducer(): MutableSharedFlow<AuthResponse> = _authResponse

    override fun emitAuthSuccess(isAccountLessAuth: Boolean) {
        emitAuthResponseMessage(
            success = true,
            message = context.getString(R.string.login_success)
        )
    }

    override fun emitAuthFailed(cause: AuthFailCause, exception: Exception?) {
        exception?.let {
            loggerService.logException(it)
        }
        val message = context.getString(when(cause){
            AuthFailCause.UNKNOWN -> R.string.login_failed_error
            AuthFailCause.NETWORK_ERROR -> R.string.login_failed_network
            AuthFailCause.LOGIN_FAILED -> R.string.login_failed
            AuthFailCause.INVALID_CREDENTIALS -> R.string.login_failed_invalid_credentials
        })

        emitAuthResponseMessage(success = false, message = message)
    }

    override fun emitAuthCancelled() {
        emitAuthResponseMessage(
            success = false,
            message = context.getString(R.string.login_cancelled)
        )
    }

    override fun emitAuthSuccessButRequireEmailVerification() {
        emitAuthResponseMessage(
            success = true,
            message = context.getString(R.string.login_success_awaiting_verification),
            awaitingVerification = true
        )
    }

    override fun emitForgotPasswordSuccessResponse() {
        emitAuthResponseMessage(
            success = false,
            message = context.getString(R.string.forgot_password_response_success)
        )
    }

    override fun emitForgotPasswordFailedResponse() {
        emitAuthResponseMessage(
            success = false,
            message = context.getString(R.string.forgot_password_response_failed)
        )
    }

    private fun emitAuthResponseMessage(
        success: Boolean,
        message: String,
        awaitingVerification: Boolean = false,
        isAnonymousAuth: Boolean = false
    ) {
        runCoroutine {
            _authResponse.emit(
                value = AuthResponse(
                    success = success,
                    message = message,
                    awaitingVerification = awaitingVerification,
                    isAnonymousAuth = isAnonymousAuth
                )
            )
        }
    }
}
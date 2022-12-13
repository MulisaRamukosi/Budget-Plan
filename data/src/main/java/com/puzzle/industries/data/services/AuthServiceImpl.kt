package com.puzzle.industries.data.services

import android.content.Context
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.puzzle.industries.data.callbacks.AuthCallback
import com.puzzle.industries.data.delegates.AuthResponseDelegate
import com.puzzle.industries.data.delegates.CoroutineDelegate
import com.puzzle.industries.data.delegates.FacebookAuthDelegate
import com.puzzle.industries.data.delegates.GoogleAuthDelegate
import com.puzzle.industries.data.delegates.implementation.AuthResponseDelegateImpl
import com.puzzle.industries.data.delegates.implementation.CoroutineDelegateImpl
import com.puzzle.industries.data.delegates.implementation.FacebookAuthDelegateImpl
import com.puzzle.industries.data.delegates.implementation.GoogleAuthDelegateImpl
import com.puzzle.industries.data.util.AuthFailCause
import com.puzzle.industries.domain.constants.AuthType
import com.puzzle.industries.domain.exceptions.UnauthorizedException
import com.puzzle.industries.domain.models.user.Account
import com.puzzle.industries.domain.models.user.AuthResponse
import com.puzzle.industries.domain.services.AuthService
import com.puzzle.industries.domain.services.LoggerService
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.tasks.await

@ActivityScoped
internal class AuthServiceImpl(
    @ActivityContext private val context: Context,
    private val loggerService: LoggerService
) : AuthService, AuthCallback,
    AuthResponseDelegate by AuthResponseDelegateImpl(context = context, loggerService = loggerService),
    CoroutineDelegate by CoroutineDelegateImpl(),
    GoogleAuthDelegate by GoogleAuthDelegateImpl(context = context),
    FacebookAuthDelegate by FacebookAuthDelegateImpl(context = context, loggerService = loggerService) {

    private var auth: FirebaseAuth = Firebase.auth

    init {
        linkAuthCallbackToGoogle(authCallback = this)
        linkAuthCallbackToFacebook(authCallback = this)
    }

    override fun authResponseHandler(): SharedFlow<AuthResponse> = authResponseProducer()

    override fun alreadyAuthed(): Boolean = auth.currentUser != null

    @kotlin.jvm.Throws(UnauthorizedException::class)
    override fun getUserAccount(): Account {
        if (auth.currentUser == null) throw UnauthorizedException("The user is not authorized")

        val user = auth.currentUser!!
        return Account(
            name = user.displayName ?: "",
            email = user.email ?: ""
        )
    }

    override suspend fun authUsingGmail() {
        authThroughGoogle()
    }

    override suspend fun authUsingFacebook() {
        authThroughFacebook()
    }

    override suspend fun authUsingEmailPassword(email: String, password: String) {
        try {
            val response = auth.signInWithEmailAndPassword(email, password).await()
            val user = response.user
            if (user != null) {
                if (user.isEmailVerified) emitAuthSuccess()
                else sendEmailVerification()
            } else {
                loggerService.logMessage("user authed but user account was not found")
                emitAuthFailed(cause = AuthFailCause.UNKNOWN)
            }

        } catch (ex: FirebaseAuthInvalidUserException) {
            when (ex.errorCode) {
                "ERROR_USER_NOT_FOUND" -> createEmailPasswordAccount(email, password)
                else -> emitAuthFailed(cause = AuthFailCause.UNKNOWN, exception = ex)
            }
        } catch (ex: FirebaseAuthInvalidCredentialsException) {
            emitAuthFailed(cause = AuthFailCause.INVALID_CREDENTIALS)
        }
    }

    override suspend fun authWithoutAccount() {
        emitAuthSuccess(isAccountLessAuth = true)
    }

    override suspend fun forgotPassword(email: String) {
        try {
            auth.sendPasswordResetEmail(email).await()
            emitForgotPasswordSuccessResponse()
        } catch (ex: Exception) {
            emitForgotPasswordFailedResponse()
        }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun onReceiveToken(token: String, authType: AuthType) {
        handleFirebaseSignInWithCredentials(token = token, authType = authType)
    }

    override fun onAuthCancelled() {
        emitAuthCancelled()
    }

    override fun onAuthFailed(ex: Exception) {
        emitAuthFailed(cause = AuthFailCause.LOGIN_FAILED, exception = ex)
    }

    private suspend fun sendEmailVerification() {
        try {
            val user = auth.currentUser
            user?.sendEmailVerification()?.await()
            emitAuthSuccessButRequireEmailVerification()
        } catch (ex: Exception) {
            emitAuthFailed(cause = AuthFailCause.LOGIN_FAILED, exception = ex)
        }
    }

    private suspend fun createEmailPasswordAccount(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            sendEmailVerification()
        } catch (ex: Exception) {
            emitAuthFailed(cause = AuthFailCause.LOGIN_FAILED, exception = ex)
        }
    }

    private fun handleFirebaseSignInWithCredentials(token: String, authType: AuthType) {
        runCoroutine {
            try {
                val credential = if (authType == AuthType.GMAIL) GoogleAuthProvider.getCredential(token, null)
                else FacebookAuthProvider.getCredential(token)

                auth.signInWithCredential(credential).await()
                emitAuthSuccess()
            } catch (ex: ApiException) {
                when (ex.statusCode) {
                    CommonStatusCodes.NETWORK_ERROR -> emitAuthFailed(cause = AuthFailCause.NETWORK_ERROR)
                    else -> emitAuthFailed(cause = AuthFailCause.UNKNOWN, exception = ex)
                }
            } catch (ex: Exception) {
                emitAuthFailed(cause = AuthFailCause.LOGIN_FAILED, exception = ex)
            }
        }
    }
}
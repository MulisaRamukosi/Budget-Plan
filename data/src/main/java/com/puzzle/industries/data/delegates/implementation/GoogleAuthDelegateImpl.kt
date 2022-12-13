package com.puzzle.industries.data.delegates.implementation

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.puzzle.industries.data.callbacks.AuthCallback
import com.puzzle.industries.data.delegates.GoogleAuthDelegate
import com.puzzle.industries.data.util.Secrets
import com.puzzle.industries.domain.constants.AuthType
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.tasks.await


class GoogleAuthDelegateImpl constructor(@ActivityContext private val context: Context) :
    GoogleAuthDelegate {

    private lateinit var authCallback: AuthCallback

    private val oneTapClient: SignInClient = Identity.getSignInClient(context)

    private val gmailSignInRequestLauncher: ActivityResultLauncher<IntentSenderRequest> =
        (context as ComponentActivity).registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val credential =
                        Identity.getSignInClient(context).getSignInCredentialFromIntent(result.data)
                    returnToken(idToken = credential.googleIdToken)
                }
                Activity.RESULT_CANCELED -> {
                    authCallback.onAuthCancelled()
                }
            }

        }

    private var oneTapGmailSignInRequestLauncher: ActivityResultLauncher<IntentSenderRequest> =
        (context as ComponentActivity).registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    returnToken(idToken = credential.googleIdToken)
                }
                Activity.RESULT_CANCELED -> {
                    authCallback.onAuthCancelled()
                }
            }
        }

    override fun linkAuthCallbackToGoogle(authCallback: AuthCallback) {
        this.authCallback = authCallback
    }

    override suspend fun authThroughGoogle() {
        try {
            val oneTapSignInRequest: BeginSignInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(Secrets.authClientID())
                        .setFilterByAuthorizedAccounts(false)
                        .build()
                )
                .build()
            val result = oneTapClient.beginSignIn(oneTapSignInRequest).await()
            val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent).build()
            oneTapGmailSignInRequestLauncher.launch(intentSenderRequest)
        } catch (_: java.lang.Exception) {
            attemptGoogleAuthFlow()
        }
    }

    private suspend fun attemptGoogleAuthFlow() {
        try {
            val googleSignInRequest = GetSignInIntentRequest.builder()
                .setServerClientId(Secrets.authClientID())
                .build()
            val result = Identity.getSignInClient(context).getSignInIntent(googleSignInRequest).await()
            gmailSignInRequestLauncher.launch(IntentSenderRequest.Builder(result).build())
        } catch (ex: Exception) {
            authCallback.onAuthFailed(ex = ex)
        }
    }

    private fun returnToken(idToken: String?){
        if (idToken != null){
            authCallback.onReceiveToken(token = idToken, authType = AuthType.GMAIL)
        } else {
            authCallback.onAuthFailed(ex = Exception("Auth succeeded but failed to retrieve token"))
        }
    }
}
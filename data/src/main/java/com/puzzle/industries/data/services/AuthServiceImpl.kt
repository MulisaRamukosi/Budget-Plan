package com.puzzle.industries.data.services

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.provider.ContactsContract.Data
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.puzzle.industries.data.util.Secrets
import com.puzzle.industries.domain.exceptions.UnauthorizedException
import com.puzzle.industries.domain.models.user.Account
import com.puzzle.industries.domain.models.user.AuthResponse
import com.puzzle.industries.domain.services.AuthService
import com.puzzle.industries.domain.services.LoggerService
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.tasks.await

class AuthServiceImpl(@ActivityContext private val context: Context, private val loggerService: LoggerService) :
    AuthService {

    private lateinit var auth: FirebaseAuth
    private val _authResponse: MutableSharedFlow<AuthResponse> = MutableSharedFlow()

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var gmailSignInRequestLauncher: ActivityResultLauncher<IntentSenderRequest>


    override fun initAuth() {
        auth = Firebase.auth
        initGoogleAuth()
    }

    private fun initGoogleAuth() {
        oneTapClient = Identity.getSignInClient(context)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(Secrets.authClientID())
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        gmailSignInRequestLauncher = (context as ComponentActivity).registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()){
            result ->
            val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
            val idToken = credential.googleIdToken
        }
    }

    override fun authResponseHandler(): SharedFlow<AuthResponse> {
        return _authResponse
    }

    override fun alreadyAuthed(): Boolean = auth.currentUser != null

    override fun getUserAccount(): Account {
        if (auth.currentUser == null) throw UnauthorizedException("The user is not authorized")

        val user = auth.currentUser!!
        return Account(
            name = user.displayName ?: "",
            email = user.email ?: ""
        )
    }

    override suspend fun authUsingGmail() {
        try {
            val result = oneTapClient.beginSignIn(signInRequest).await()
            val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent).build()
            gmailSignInRequestLauncher.launch(intentSenderRequest)
        }
        catch (ex: java.lang.Exception){
            loggerService.logException(ex)
        }
    }

    override suspend fun authUsingFacebook() {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        auth.signOut()
    }
}
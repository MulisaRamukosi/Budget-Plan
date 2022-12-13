package com.puzzle.industries.data.delegates.implementation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginClient
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.puzzle.industries.data.callbacks.AuthCallback
import com.puzzle.industries.data.delegates.FacebookAuthDelegate
import com.puzzle.industries.data.util.Secrets
import com.puzzle.industries.domain.constants.AuthType
import com.puzzle.industries.domain.services.LoggerService
import dagger.hilt.android.qualifiers.ApplicationContext

internal class FacebookAuthDelegateImpl constructor(
    @ApplicationContext private val context: Context,
    loggerService: LoggerService
) : FacebookAuthDelegate {

    private lateinit var authCallback: AuthCallback
    private lateinit var launcher: ActivityResultLauncher<Collection<String>>

    init {
        FacebookSdk.setApplicationId(Secrets.facebookAppId())
        FacebookSdk.setClientToken(Secrets.facebookClientToken())
        try {
            FacebookSdk.sdkInitialize(context)
            launcher = (context as ComponentActivity).registerForActivityResult(
            LoginManager.getInstance()
                .createLogInActivityResultContract(CallbackManager.Factory.create()),
            ) {
                when(it.resultCode){
                    Activity.RESULT_OK -> {
                        val result = getLoginResultFromIntent(it.data)
                        if (result == null){
                            authCallback.onAuthFailed(ex = Exception("Facebook: Authenticated with no result"))
                        }
                        else{
                            val token = result.token
                            if (token == null){
                                authCallback.onAuthFailed(ex = Exception("Facebook: Authenticated with no token"))
                            }
                            else{
                                authCallback.onReceiveToken(token =token.token, authType = AuthType.FACEBOOK)
                            }

                        }
                    }
                    Activity.RESULT_CANCELED -> authCallback.onAuthCancelled()
                    else -> authCallback.onAuthFailed(ex = Exception("Failed to authenticate using facebook"))
                }
            }
        } catch (ex: Exception) {
            loggerService.logException(exception = ex)
        }
    }

    override fun linkAuthCallbackToFacebook(authCallback: AuthCallback) {
        this.authCallback = authCallback
    }

    override suspend fun authThroughFacebook() {
        launcher.launch(listOf("public_profile", "email"))
    }

    private fun getLoginResultFromIntent(intent: Intent?): LoginClient.Result? {
        if (intent == null) {
            return null
        }
        intent.setExtrasClassLoader(LoginClient.Result::class.java.classLoader)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("com.facebook.LoginFragment:Result", LoginClient.Result::class.java)
        } else {
            intent.getParcelableExtra("com.facebook.LoginFragment:Result")
        }
    }
}
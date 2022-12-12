package com.puzzle.industries.data.delegates.implementation

import android.app.Activity
import android.content.Context
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.puzzle.industries.data.callbacks.AuthCallback
import com.puzzle.industries.data.delegates.FacebookAuthDelegate
import com.puzzle.industries.data.util.Secrets
import dagger.hilt.android.qualifiers.ApplicationContext

internal class FacebookAuthDelegateImpl constructor(@ApplicationContext private val context: Context) :
    FacebookAuthDelegate, FacebookCallback<LoginResult> {
    private lateinit var authCallback: AuthCallback

    init {
        FacebookSdk.setApplicationId(Secrets.facebookAppId())
        FacebookSdk.setClientToken(Secrets.facebookClientToken())
        try {
            FacebookSdk.sdkInitialize(context)
        }
        catch (ex: Exception){
            var c = ""
        }

        LoginManager.getInstance().registerCallback(CallbackManager.Factory.create(), this)
    }

    override fun linkAuthCallbackToFacebook(authCallback: AuthCallback) {
        this.authCallback = authCallback
    }

    override suspend fun authThroughFacebook() {
        LoginManager.getInstance().logInWithReadPermissions((context as Activity), listOf("public_profile"))
    }

    override fun onCancel() {
        authCallback.onAuthCancelled()
    }

    override fun onError(error: FacebookException) {
        authCallback.onAuthFailed(ex = error)
    }

    override fun onSuccess(result: LoginResult) {
        authCallback.onReceiveToken(idToken = result.accessToken.token)
    }
}
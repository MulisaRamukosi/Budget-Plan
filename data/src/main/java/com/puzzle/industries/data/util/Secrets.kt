package com.puzzle.industries.data.util

object Secrets {

    init {
        System.loadLibrary("native-lib")
    }

    external fun authClientID(): String
    external fun facebookAppId(): String
    external fun facebookClientToken(): String
}
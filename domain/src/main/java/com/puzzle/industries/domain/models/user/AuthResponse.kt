package com.puzzle.industries.domain.models.user

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val awaitingVerification: Boolean,
    val isAnonymousAuth: Boolean,
    val exception: java.lang.Exception? = null
)

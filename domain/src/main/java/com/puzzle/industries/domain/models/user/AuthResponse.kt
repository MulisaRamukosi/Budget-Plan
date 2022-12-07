package com.puzzle.industries.domain.models.user

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val exception: java.lang.Exception?
)

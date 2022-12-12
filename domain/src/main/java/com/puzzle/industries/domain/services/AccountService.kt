package com.puzzle.industries.domain.services

interface AccountService {
    suspend fun registerAccount()
    suspend fun deleteAccount()
}
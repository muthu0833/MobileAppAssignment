package com.inila.mobileappassignment.data.repository

import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor() {
    suspend fun login(email: String, password: String): Boolean {
        // Mock network delay
        delay(1000)
        // Simple mock authentication
        return email == "test@example.com" && password == "password123"
    }
}

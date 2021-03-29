package com.example.messengerapp.repository.login

import com.google.firebase.auth.FirebaseUser

interface LogInRepo {
    suspend fun login(username: String, password: String) : FirebaseUser?
}
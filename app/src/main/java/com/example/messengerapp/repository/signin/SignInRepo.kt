package com.example.messengerapp.repository.signin

import com.google.firebase.auth.FirebaseUser

interface SignInRepo {
    suspend fun login(username: String, password: String): FirebaseUser?
}
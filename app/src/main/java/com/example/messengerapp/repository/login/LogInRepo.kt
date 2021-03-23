package com.example.messengerapp.repository.login

import com.example.messengerapp.data.remote.signin.LogInDataSource
import com.google.firebase.auth.FirebaseUser

interface LogInRepo {
    suspend fun login(username: String, password: String): FirebaseUser?
}
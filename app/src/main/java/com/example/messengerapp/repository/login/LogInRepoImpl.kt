package com.example.messengerapp.repository.login

import com.example.messengerapp.data.remote.signin.LogInDataSource
import com.google.firebase.auth.FirebaseUser

class LogInRepoImpl(private val logInDataSource: LogInDataSource): LogInRepo {
    override suspend fun login(username: String, password: String): FirebaseUser? = logInDataSource.logIn(username, password)

}
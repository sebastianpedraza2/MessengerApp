package com.example.messengerapp.repository.signin

import com.example.messengerapp.data.remote.signin.SignInDataSource
import com.example.messengerapp.repository.signin.SignInRepo
import com.google.firebase.auth.FirebaseUser

class SignInRepoImpl(private val signInDataSource: SignInDataSource): SignInRepo {
    override suspend fun login(username: String, password: String): FirebaseUser? = signInDataSource.signIn(username, password)

}
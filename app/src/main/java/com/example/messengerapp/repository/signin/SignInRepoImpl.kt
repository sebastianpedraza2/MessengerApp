package com.example.messengerapp.repository.signin

import android.graphics.Bitmap
import com.example.messengerapp.data.remote.signin.SignInDataSource
import com.example.messengerapp.repository.signin.SignInRepo
import com.google.firebase.auth.FirebaseUser

class SignInRepoImpl(private val signInDataSource: SignInDataSource): SignInRepo {
    override suspend fun login(username: String, password: String, bitmap: Bitmap,
                               name: String): FirebaseUser? = signInDataSource.signIn(username, password, bitmap, name)

}
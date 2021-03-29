package com.example.messengerapp.data.remote.signin

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await


class SignInDataSource {
    suspend fun signIn(username: String, password: String): FirebaseUser? {
        val loginResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(username, password).await()
        return loginResult.user
    }
}
package com.example.messengerapp.data.remote.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class LogInDataSource {
    suspend fun login(username: String, password: String) : FirebaseUser?{
        val user = FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password).await()
        return user.user
    }
}
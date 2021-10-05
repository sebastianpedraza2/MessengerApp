package com.example.messengerapp.repository.signin

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseUser

interface SignInRepo {
    suspend fun login(username: String, password: String, bitmap: Bitmap,
                      name: String): FirebaseUser?
}
package com.example.messengerapp.data.remote.signin

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.example.messengerapp.data.model.UserLogin
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*



class SignInDataSource {
    suspend fun signIn(
        username: String,
        password: String,
        bitmap: Bitmap,
        name: String
    ): FirebaseUser? {
        val loginResult =
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Log.d("FirebaseSignin", "signIn: There was a problem ERRORRRR")
                        return@addOnCompleteListener
                    }
                    Log.d("FirebaseSignin", "signIn: Storing image...")
                    uploadImageToFireStorage(bitmap, name)
                }.addOnFailureListener {
                    Log.d("FirebaseSignin", "signIn: There was a problem:  ${it.message}")
                }
        return loginResult.result?.user
    }

    private fun uploadImageToFireStorage(bitmap: Bitmap, name: String) {
        val storageRef = FirebaseStorage.getInstance().getReference("/images/${UUID.randomUUID()}")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = storageRef.putBytes(data)
        uploadTask.continueWithTask {
            if (!it.isSuccessful) {
                it.exception?.let { e ->
                    Log.d(
                        "FirebaseSignin",
                        "uploadImageToFireStorage: error  trying to upload image to firestorage : ${e.message} "
                    )
                    throw e

                }
            }
            storageRef.downloadUrl
        }.addOnCompleteListener {
            val authRef = FirebaseAuth.getInstance().uid
            val firebaseDatabase = FirebaseDatabase.getInstance().reference
            val userRef =
                firebaseDatabase.child("/user/$authRef")  //uso el identificador que da fireauth para guardar la info de los usuarios
            userRef.setValue(UserLogin(name, authRef, it.result.toString())).addOnSuccessListener {
                Log.d(
                    "FirebaseSignin",
                    "User $name saved} "
                )
            }
            Log.d(
                "FirebaseSignin",
                "Saved ImageUrl: ${it.result.toString()} "
            )
        }


    }
}
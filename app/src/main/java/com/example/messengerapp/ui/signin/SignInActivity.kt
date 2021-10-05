package com.example.messengerapp.ui.signin

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.messengerapp.R
import com.example.messengerapp.data.remote.signin.SignInDataSource
import com.example.messengerapp.databinding.ActivitySignInBinding
import com.example.messengerapp.presentation.signin.SignInViewModel
import com.example.messengerapp.presentation.signin.SignViewModelFactory
import com.example.messengerapp.repository.signin.SignInRepoImpl
import com.example.messengerapp.utils.Resource

class SignInActivity : AppCompatActivity() {
    private val REQUESTCODE = 1
    private lateinit var imgbitmap: Bitmap
    private val signInViewModel by viewModels<SignInViewModel> {
        SignViewModelFactory(SignInRepoImpl(SignInDataSource()))
    }
    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnGoToLogin.setOnClickListener {
            finish()
        }

        pickProfileImage()
        getUserAndPassword()
    }

    private fun pickProfileImage() {
        binding.profileImageView.setOnClickListener {
            val takePictureIntent = Intent(Intent.ACTION_PICK)
            takePictureIntent.type = "image/*"
            startActivityForResult(takePictureIntent, REQUESTCODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUESTCODE && resultCode == RESULT_OK) {
//               val inputstream = ContentResolver().openInputStream(data.data)
            //imgbitmap = data?.extras?.get("data") as Bitmap
//            binding.profileImageView.setImageBitmap(imgbitmap)
            val uri = data?.data
            imgbitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            binding.profileImageView.setImageURI(data?.data)
        }
    }

    private fun getUserAndPassword() {
        binding.btnRegister.setOnClickListener {
            val username = binding.editTxtEmail.text.toString().trim()
            val password = binding.editTxtPassword.text.toString().trim()
            val name = binding.editTxtName.text.toString().trim()
            val repeatPassword = binding.editTxtRepeatPassword.text.toString().trim()
            validateinfo(username, password, repeatPassword)
            createUser(username, password, name)
        }
    }

    private fun validateinfo(user: String, pass: String, repeatpass: String) {
        if (user.isEmpty()) {
            binding.editTxtEmail.error = "Please enter a value"
            return
        }
        if (pass.isEmpty()) {
            binding.editTxtPassword.error = "Please enter a password"
            return
        }
        if (repeatpass.isEmpty()) {
            binding.editTxtRepeatPassword.error = "Please enter a password"
            return
        }
        if (repeatpass != pass) {
            binding.editTxtRepeatPassword.error = "Enter the same password as before"
            return
        }
    }

    private fun createUser(username: String, password: String, name: String) {
        signInViewModel.login(username, password, imgbitmap, name).observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.btnRegister.isEnabled = false
                    binding.progressBarSignin.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBarSignin.visibility = View.GONE
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(
                        this,
                        "The user has been created successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Failure -> {
                    binding.progressBarSignin.visibility = View.GONE
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(this, "Error : ${it.exception}", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }

}
package com.example.messengerapp.ui.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.messengerapp.R
import com.example.messengerapp.data.remote.signin.LogInDataSource
import com.example.messengerapp.databinding.ActivityMainBinding
import com.example.messengerapp.databinding.ActivitySignInBinding
import com.example.messengerapp.presentation.login.LogInViewModel
import com.example.messengerapp.presentation.login.LoginViewModelFactory
import com.example.messengerapp.repository.login.LogInRepoImpl
import com.example.messengerapp.utils.Resource

class SignInActivity : AppCompatActivity() {
    private val signInViewModel by viewModels<LogInViewModel> {
        LoginViewModelFactory(LogInRepoImpl(LogInDataSource()))
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
        getUserAndPassword()
    }

    private fun getUserAndPassword() {
        binding.btnRegister.setOnClickListener {
            val username = binding.editTxtEmail.text.toString().trim()
            val password = binding.editTxtPassword.text.toString().trim()
            val repeatPassword = binding.editTxtRepeatPassword.text.toString().trim()
            validateinfo(username, password, repeatPassword)
            createUser(username, password)
        }
    }

    private fun validateinfo(user: String, pass: String, repeatpass : String) {
        if (user.isEmpty()) {
            binding.editTxtEmail.error = "Please enter a value"
            return
        }
        if (pass.isEmpty()) {
            binding.editTxtPassword.error = "Please enter a password"
            return
        }
        if(repeatpass.isEmpty()){
            binding.editTxtRepeatPassword.error = "Please enter a password"
            return
        }
        if(repeatpass != pass){
            binding.editTxtRepeatPassword.error = "Enter the same password as before"
            return
        }
    }

    private fun createUser(username: String, password: String) {
        signInViewModel.login(username, password).observe(this, Observer {
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
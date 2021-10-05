package com.example.messengerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.messengerapp.data.remote.login.LogInDataSource
import com.example.messengerapp.databinding.ActivityMainBinding
import com.example.messengerapp.presentation.login.LogInViewModel
import com.example.messengerapp.presentation.login.LogInViewModelFactory
import com.example.messengerapp.repository.login.LogInRepoImpl
import com.example.messengerapp.ui.signin.SignInActivity
import com.example.messengerapp.utils.Resource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Todo change to koin di injection
     */
    private val loginViewModel by viewModels<LogInViewModel> {
        LogInViewModelFactory(LogInRepoImpl(LogInDataSource()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnGoToSingin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        getUsernameAndPassword()

    }

    private fun getUsernameAndPassword() {
        binding.btnLogin.setOnClickListener {
            val user = binding.editTxtEmailLogin.text.toString().trim()
            val pass = binding.editTxtPasswordLogin.text.toString().trim()
            validateUsernameAndPassword(user, pass)
            goToLogin(user, pass)
        }
    }

    private fun validateUsernameAndPassword(user: String, pass: String) {
        if (user.isEmpty()) {
            binding.editTxtEmailLogin.error = "Please enter a value"
            return
        }
        if (pass.isEmpty()) {
            binding.editTxtPasswordLogin.error = "Please enter a password"
            return
        }
    }

    private fun goToLogin(user: String, pass: String) {
        loginViewModel.login(user, pass).observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBarLogin.visibility = View.VISIBLE
                    binding.btnLogin.isEnabled = false
                }
                is Resource.Success -> {
                    binding.progressBarLogin.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                    Toast.makeText(this, "Ha ingresado como : ${it.data}", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("firebaselogin", "Login Exitoso: ${it.data}")
                }
                is Resource.Failure -> {
                    binding.progressBarLogin.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                    Toast.makeText(this, "Error : ${it.exception}", Toast.LENGTH_SHORT).show()
                    Log.d("firebaselogin", "Login error: ${it.exception}")
                }

            }
        })
    }
}
package com.example.messengerapp.ui.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.messengerapp.R
import com.example.messengerapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
   // private lateinit var binding : ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val btnGotoLogin = findViewById<TextView>(R.id.btn_go_to_login)
       btnGotoLogin.setOnClickListener{
            finish()
        }
    }

}
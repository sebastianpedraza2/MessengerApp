package com.example.messengerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.messengerapp.databinding.ActivityMainBinding
import com.example.messengerapp.ui.signin.SignInActivity

class MainActivity : AppCompatActivity() {
//private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnGoToSignin = findViewById<TextView>(R.id.btn_go_to_singin)
        btnGoToSignin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

    }
}
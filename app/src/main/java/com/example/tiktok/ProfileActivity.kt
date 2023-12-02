package com.example.tiktok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiktok.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var profileUserId: String
    lateinit var currentUserId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //getting profile userId from MainActivity.kt
        profileUserId = intent.getStringExtra("profile_user_id")!!
        // getting current userId from Firebase
        currentUserId = FirebaseAuth.getInstance().currentUser?.uid!!
    }
}
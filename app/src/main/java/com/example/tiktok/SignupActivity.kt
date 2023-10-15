package com.example.tiktok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.tiktok.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener {
            signup()
        }
    }

    fun signup() {
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.confirmPasswordInput.text.toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailInput.setError("Email not valid")
            return
        }
        if(password.length<6) {
            binding.passwordInput.setError("Minimum 6 letters for password")
            return
        }
        if(password!=confirmPassword){
            binding.confirmPasswordInput.setError("Password not matched")
            return
        }
        signupWithFirebase()
    }

    fun signupWithFirebase() {

    }
}
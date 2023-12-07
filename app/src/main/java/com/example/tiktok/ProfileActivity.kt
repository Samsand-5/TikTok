package com.example.tiktok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiktok.databinding.ActivityProfileBinding
import com.example.tiktok.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var profileUserId: String
    lateinit var currentUserId: String
    lateinit var profileUserModel: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //getting profile userId from MainActivity.kt
        profileUserId = intent.getStringExtra("profile_user_id")!!
        // getting current userId from Firebase
        currentUserId = FirebaseAuth.getInstance().currentUser?.uid!!

        if(profileUserId==currentUserId) {
            binding.profileBtn.text = "LOG OUT"
        }
        else{

        }
        getProfileDataFromFirebase()
    }

     fun getProfileDataFromFirebase() {
         Firebase.firestore.collection("users")
             .document(profileUserId)
             .get()
             .addOnSuccessListener {
                 profileUserModel = it.toObject(UserModel::class.java)!!
                 setUi()
             }
    }
    fun setUi(){

    }

}
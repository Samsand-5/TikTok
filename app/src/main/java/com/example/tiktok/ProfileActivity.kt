package com.example.tiktok

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tiktok.databinding.ActivityProfileBinding
import com.example.tiktok.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var profileUserId: String
    lateinit var currentUserId: String
    lateinit var profileUserModel: UserModel
    lateinit var photoLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        photoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            if(result.resultCode == RESULT_OK){
                //upload photo

            }
        }
        //getting profile userId from MainActivity.kt
        profileUserId = intent.getStringExtra("profile_user_id")!!
        // getting current userId from Firebase
        currentUserId = FirebaseAuth.getInstance().currentUser?.uid!!

        if(profileUserId==currentUserId) {
            binding.profileBtn.text = "LOG OUT"
            binding.profileBtn.setOnClickListener {
                logout()
            }
            binding.profilePic.setOnClickListener {
                checkPermissionAndPickPhoto()
            }
        }
        else{
            // other profile
        }
        getProfileDataFromFirebase()
    }

    fun checkPermissionAndPickPhoto(){
        var readExternalPhoto : String=""
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            readExternalPhoto = android.Manifest.permission.READ_MEDIA_IMAGES
        }
        else{
            readExternalPhoto = android.Manifest.permission.READ_EXTERNAL_STORAGE
        }
        if(ContextCompat.checkSelfPermission(this,readExternalPhoto) == PackageManager.PERMISSION_GRANTED){
            openPhotoPicker()
        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(readExternalPhoto),100)
        }
    }

    fun openPhotoPicker(){

    }

    fun logout(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this,LoginActivity::class.java)
        //when logout it will have to login again to get the account
        //thus, when logout button is pressed and then we closed the app then again we open the app it will show
        //LoginActivity.kt class activity
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
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
        profileUserModel.apply {
            Glide.with(binding.profilePic).load(profilepic)
                .apply(RequestOptions().placeholder(R.drawable.icon_profile))
                .into(binding.profilePic)
            binding.profileUsername.text = "@" + username
            binding.progressBar.visibility = View.INVISIBLE
            binding.followingCount.text = followingList.size.toString()
            binding.followersCount.text = followerList.size.toString()
            Firebase.firestore.collection("videos")
                .whereEqualTo("uploaderid",profileUserId)
                .get().addOnSuccessListener {
                    binding.postsCount.text = it.size().toString()
                }
        }
    }

}
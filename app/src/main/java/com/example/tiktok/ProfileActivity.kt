package com.example.tiktok

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tiktok.databinding.ActivityProfileBinding
import com.example.tiktok.model.UserModel
import com.example.tiktok.util.UiUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

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
                result.data!!.data?.let { uploadToFireStore(it) }
            }
        }
        //getting profile userId from MainActivity.kt
        profileUserId = intent.getStringExtra("profile_user_id")!!
        // getting current userId from Firebase
        currentUserId = FirebaseAuth.getInstance().currentUser?.uid!!

        if(profileUserId==currentUserId) {
            //current profile user
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
            binding.profileBtn.text = "Follow"
            binding.profileBtn.setOnClickListener {
                followUnfollowUser()
            }
        }
        getProfileDataFromFirebase()
    }

    fun followUnfollowUser(){
        Firebase.firestore.collection("users")
            .document(currentUserId)
            .get()
            .addOnSuccessListener {
                val currentUserModel = it.toObject(UserModel::class.java)!!
                if(profileUserModel.followerList.contains(currentUserId)){
                    binding.profileBtn.text = "Unfollow"
                }
            }
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

    fun uploadToFireStore(photoUri: Uri){
        binding.progressBar.visibility = View.VISIBLE
        val photoRef = FirebaseStorage.getInstance()
            .reference.child("profilepic/"+ currentUserId) //create folder of profile picture and use is current User
        photoRef.putFile(photoUri)
            .addOnSuccessListener{
                photoRef.downloadUrl.addOnSuccessListener {downloadUrl->
                    //video model store in fireStore
                    postToFireStore(downloadUrl.toString())
                }
            }
    }

    fun postToFireStore(url: String){
        Firebase.firestore.collection("users")
            .document(currentUserId)
            .update("profilepic",url)
            .addOnSuccessListener {
                getProfileDataFromFirebase()
            }
    }

    fun openPhotoPicker(){
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        intent.type = "video/*"
        photoLauncher.launch(intent)
        UiUtil.showToast(this,"Video Picker")
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
                .circleCrop()
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
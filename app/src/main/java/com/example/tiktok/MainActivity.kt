package com.example.tiktok

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiktok.adapter.VideoListAdapter
import com.example.tiktok.databinding.ActivityMainBinding
import com.example.tiktok.model.VideoModel
import com.example.tiktok.util.UiUtil
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: VideoListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavBar.setOnItemSelectedListener {menuItem->
            when(menuItem.itemId){
                R.id.bottom_menu_home->{
                    UiUtil.showToast(this,"Home")
                }
                R.id.bottom_menu_add_video->{
                    startActivity(Intent(this,VideoUploadActivity::class.java))
                }
                R.id.bottom_menu_profile->{
                    val intent = Intent(this,ProfileActivity::class.java)
                    intent.putExtra("profile_user_id", FirebaseAuth.getInstance().currentUser?.uid)
                    startActivity(intent)
                }
            }
            false
        }
        setUpViewPager()
    }

    private fun setUpViewPager() {
        //options will get videos from firestore videos collections and convert it into videoModel and we will  get the video
        val options = FirestoreRecyclerOptions.Builder<VideoModel>()
            .setQuery(
                Firebase.firestore.collection("videos"),
                VideoModel::class.java
            ).build()

        adapter = VideoListAdapter(options)
        binding.viewPager.adapter = adapter
    }

    //listener for FireStoreRecyclerOptions
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
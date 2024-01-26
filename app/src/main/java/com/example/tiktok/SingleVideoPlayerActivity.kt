package com.example.tiktok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiktok.adapter.VideoListAdapter
import com.example.tiktok.databinding.ActivitySingleVideoPlayerBinding
import com.example.tiktok.model.VideoModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SingleVideoPlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivitySingleVideoPlayerBinding
    lateinit var videoId: String
    lateinit var adapter: VideoListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        videoId = intent.getStringExtra("videoId")!!
        setupViewPager()
    }

    private fun setupViewPager() {
        val options = FirestoreRecyclerOptions.Builder<VideoModel>()
            .setQuery(
                Firebase.firestore.collection("videos")
                    .whereEqualTo("videoId",videoId),
                VideoModel::class.java
            ).build()
        adapter = VideoListAdapter(options)
        binding.viewPager
    }
}
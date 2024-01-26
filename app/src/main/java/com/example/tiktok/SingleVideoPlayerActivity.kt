package com.example.tiktok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiktok.databinding.ActivitySingleVideoPlayerBinding

class SingleVideoPlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivitySingleVideoPlayerBinding
    lateinit var videoId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        videoId = intent.getStringExtra("videoId")!!
        setupViewPager()
    }

    private fun setupViewPager() {
        TODO("Not yet implemented")
    }
}
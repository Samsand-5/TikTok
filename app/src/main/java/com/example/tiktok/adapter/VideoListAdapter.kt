package com.example.tiktok.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.tiktok.databinding.VideoItemRowBinding
import com.example.tiktok.model.VideoModel

class VideoListAdapter {

    inner class VideoViewHolder(private var binding : VideoItemRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindVideo(videoModel: VideoModel) {
            //bind video
            binding.videoView.apply {
                setVideoPath(videoModel.url)
                setOnPreparedListener {
                    it.start()
                    it.isLooping = true
                }
                //play & pause
                setOnClickListener {
                    if(isPlaying){
                        pause()
                        binding.pauseIcon.visibility=View.VISIBLE
                    }
                    else{
                        resume()
                        binding.pauseIcon.visibility=View.GONE
                    }
                }
            }
        }
    }
}
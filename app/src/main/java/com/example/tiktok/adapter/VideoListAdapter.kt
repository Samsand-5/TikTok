package com.example.tiktok.adapter

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

                }
            }
        }
    }
}
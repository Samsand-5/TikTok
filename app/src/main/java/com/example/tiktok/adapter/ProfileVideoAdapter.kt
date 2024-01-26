package com.example.tiktok.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiktok.SingleVideoPlayerActivity
import com.example.tiktok.databinding.ProfileVideoItemRowBinding
import com.example.tiktok.model.VideoModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ProfileVideoAdapter(options: FirestoreRecyclerOptions<VideoModel>)
    : FirestoreRecyclerAdapter<VideoModel,ProfileVideoAdapter.VideoViewHolder>(options)
{

    inner class VideoViewHolder(private val binding: ProfileVideoItemRowBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(video: VideoModel){
            Glide.with(binding.thumbnailImageView)
                .load(video.url)
                .into(binding.thumbnailImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ProfileVideoItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, model: VideoModel) {
        TODO("Not yet implemented")
    }
}
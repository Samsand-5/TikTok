package com.example.tiktok.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tiktok.databinding.VideoItemRowBinding
import com.example.tiktok.model.UserModel
import com.example.tiktok.model.VideoModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VideoListAdapter(
    options: FirestoreRecyclerOptions<VideoModel>
) : FirestoreRecyclerAdapter<VideoModel,VideoListAdapter.VideoViewHolder>(options){

    inner class VideoViewHolder(private var binding : VideoItemRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindVideo(videoModel: VideoModel) {
            //bind user data

            Firebase.firestore.collection("users")
                .document(videoModel.uploaderId)
                .get().addOnSuccessListener {
                    val userModel = it?.toObject(UserModel::class.java)
                    userModel?.apply {
                        binding.usernameView.text = username
                        //bind profilePic
                    }
                }
            binding.captionView.text = videoModel.title

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
                        start()
                        binding.pauseIcon.visibility=View.GONE
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = VideoItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, model: VideoModel) {
        holder.bindVideo(model)
    }
}
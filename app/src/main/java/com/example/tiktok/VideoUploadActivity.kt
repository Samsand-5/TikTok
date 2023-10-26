package com.example.tiktok

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.tiktok.databinding.ActivityVideoUploadBinding

class VideoUploadActivity : AppCompatActivity() {

    lateinit var binding: ActivityVideoUploadBinding
    private var selectedVideoUri: Uri?= null
    lateinit var videoLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if(result.resultCode == RESULT_OK){
                selectedVideoUri = result.data?.data
            }
        }
        binding.uploadView.setOnClickListener {
            checkPermissionAndOpenVideoPicker()
        }
    }

    private fun checkPermissionAndOpenVideoPicker() {
        var readExternalVideo : String=""
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            readExternalVideo = android.Manifest.permission.READ_MEDIA_VIDEO
        }
    }
}
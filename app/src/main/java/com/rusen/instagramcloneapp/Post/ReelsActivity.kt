package com.rusen.instagramcloneapp.Post

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.rusen.instagramcloneapp.HomeActivity
import com.rusen.instagramcloneapp.Models.Reel
import com.rusen.instagramcloneapp.databinding.ActivityReelsBinding
import com.rusen.instagramcloneapp.utils.POST
import com.rusen.instagramcloneapp.utils.REEL
import com.rusen.instagramcloneapp.utils.uploadVideo

class ReelsActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }
    private lateinit var videoUrl:String
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadVideo(uri, REEL) { url ->
                if (url != null) {
                    videoUrl = url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.ivSelectVideo.setOnClickListener {
            launcher.launch("video/*")
        }
        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }
        binding.postButton.setOnClickListener {
            val reel: Reel = Reel(videoUrl!!, binding.tvCaption.editText?.text.toString())

            Firebase.firestore.collection(POST).document().set(reel).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).document().set(reel).addOnSuccessListener {
                    startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                    finish()
                }

            }
        }
    }
}
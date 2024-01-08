package com.rusen.instagramcloneapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rusen.instagramcloneapp.common.viewBinding
import com.rusen.instagramcloneapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.statusBarColor = Color.TRANSPARENT

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }, 3000)
    }
}
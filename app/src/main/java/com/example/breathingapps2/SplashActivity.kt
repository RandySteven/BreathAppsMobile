package com.example.breathingapps2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.breathingapps2.databinding.ActivitySplashBinding
import com.example.breathingapps2.repository.Repository
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        Repository.addDataSong()
//        Repository.addDataToSongsImage()
//        Repository.addDataVideo()
        delayAndToMain()
    }

    private fun delayAndToMain(){
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity<MainActivity>()
            finishAffinity()
        }, 1200)
    }
}
package com.example.breathingapps2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.breathingapps2.api.ApiClient
import com.example.breathingapps2.databinding.ActivitySplashBinding
import com.example.breathingapps2.datamodels.GetArticleResponse
import com.example.breathingapps2.models.Article
import com.example.breathingapps2.repository.Repository
import com.google.gson.Gson
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class SplashActivity : AppCompatActivity() {

    private var articles : MutableList<Article> = mutableListOf<Article>()
    private var sizeTemp : Int = 0

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
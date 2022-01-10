package com.example.breathingapps2

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.breathingapps2.databinding.VideoDetailBinding
import com.example.breathingapps2.models.Video
import org.jetbrains.anko.startActivity

class VideoActivity : AppCompatActivity() {

    companion object{
        const val KEY_VIDEOS = "key_videos"
        const val KEY_POSITION = "key_position"
    }

    private lateinit var videoDetailBinding: VideoDetailBinding
    private lateinit var handler : Handler
    private lateinit var mediaController : MediaController
    private var video : Video ?= null

    private var videos : MutableList<Video> ?= null
    private var position = 0


    override fun onResume() {
        super.onResume()
        if(video != null){
            videoDetailBinding?.videoVideo?.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        videoDetailBinding?.videoVideo?.stopPlayback()
        video = null
    }

    override fun onPause() {
        super.onPause()
        videoDetailBinding?.videoVideo?.pause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videoDetailBinding = VideoDetailBinding.inflate(layoutInflater)
        mediaController = MediaController(this)
        setContentView(videoDetailBinding.root)

        getData()
        onClick()
    }

    private fun getData(){
        if(intent != null){
            videos = intent.getParcelableArrayListExtra(KEY_VIDEOS)
            position = intent.getIntExtra(KEY_POSITION, 0)
            videos?.let{
                video = it[position]
                initView(video!!)
            }
        }
    }

    private fun onClick(){
        videoDetailBinding?.backArrow?.setOnClickListener {
            finish()
            startActivity<MainActivity>()
        }
    }

    private fun initView(video: Video){
        videoDetailBinding?.detailVideoTitle.text = video.videoTitle
        videoDetailBinding?.videoVideo.setVideoURI(Uri.parse(video.uriVideo))
        videoDetailBinding?.detailVideoDescription.text = video.videoDesc

        mediaController.setAnchorView(videoDetailBinding?.videoVideo)
        videoDetailBinding?.videoVideo.setMediaController(mediaController)
        videoDetailBinding?.videoVideo.start()
    }
}
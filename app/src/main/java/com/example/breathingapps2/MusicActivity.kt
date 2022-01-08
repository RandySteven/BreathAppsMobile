package com.example.breathingapps2

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.breathingapps2.databinding.MusicDetailBinding
import com.example.breathingapps2.models.Song
import com.example.musicapps.utils.toSongTime
import java.lang.IllegalStateException

class MusicActivity : AppCompatActivity() {

    companion object{
        const val KEY_SONGS = "key_songs"
        const val KEY_POSITION = "key_position"
    }

    private lateinit var musicBinding : MusicDetailBinding
    private var position = 0
    private var songs : MutableList<Song> ?= null
    private var musicPlayer : MediaPlayer ?= null
    private lateinit var handler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        musicBinding = MusicDetailBinding.inflate(layoutInflater)
        setContentView(musicBinding.root)

        init()
        getData()
        onClick()
    }

    override fun onPause() {
        super.onPause()
        musicPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayer?.stop()
        musicPlayer = null
    }

    override fun onResume() {
        super.onResume()
        if(musicPlayer != null && musicPlayer?.isLooping!!){
            musicPlayer?.start()
        }
    }

    private fun init(){
        handler = Handler(Looper.getMainLooper())
        musicPlayer = MediaPlayer()
        musicPlayer?.setAudioAttributes(
            AudioAttributes
                .Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
    }

    private fun getData(){
        if(intent != null){
            songs = intent.getParcelableArrayListExtra(KEY_SONGS)
            position = intent.getIntExtra(KEY_POSITION, 0)
            songs?.let{
                val song = it[position]
                initView(song)
            }
        }
    }

    private fun checkButtonSong(){
        if(position == 0){
            musicBinding.btnPrevSong.setColorFilter(ContextCompat.getColor(this, android.R.color.darker_gray))
            musicBinding.btnPrevSong.isEnabled = false
        }else if(position > 0 && position < songs?.size?.minus(1)!!){
            musicBinding.btnPrevSong.setColorFilter(ContextCompat.getColor(this, android.R.color.white))
            musicBinding.btnPrevSong.isEnabled = true

            musicBinding.btnNextSong.setColorFilter(ContextCompat.getColor(this, android.R.color.white))
            musicBinding.btnNextSong.isEnabled = true
        }else{
            musicBinding.btnNextSong.setColorFilter(ContextCompat.getColor(this, android.R.color.darker_gray))
            musicBinding.btnNextSong.isEnabled = false
        }
    }

    private fun initView(song : Song){
        checkButtonSong()

        musicBinding.tvNameSong.text = song.nameSong
        Glide.with(this)
            .load(song.imageSong)
            .placeholder(android.R.color.darker_gray)
            .into(musicBinding.ivPlaySong)

        playSong(song)
    }

    private fun playNextSong(){
        val songSize = songs?.size?.minus(1)
        if(position < songSize!!){
            position += 1
            val song = songs?.get(position)
            musicPlayer?.reset()
            if(song != null){
                initView(song)
            }
        }
    }

    private fun playPrevSong(){
        if(position > 0){
            position -= 1
            val song = songs?.get(position)
            musicPlayer?.reset()
            if(song != null){
                initView(song)
            }
        }
    }

    private fun playSong(song: Song){
        try{
            musicPlayer?.setDataSource(song.uriSong)
            musicPlayer?.setOnPreparedListener {
                it.start()
                musicBinding?.seekBarPlaySong.max = it?.duration!!
                musicBinding.tvStartEnd.text = it?.duration.toSongTime()
                checkMusicButton()
            }

            musicPlayer?.prepareAsync()

            handler.postDelayed(object : Runnable{
                override fun run() {
                    try{
                        musicBinding.seekBarPlaySong.progress = musicPlayer?.currentPosition!!
                        handler.postDelayed(this, 1000)
                    }catch (e : Exception){
                        Log.e("MusicActivity", "run: ${e.message}", )
                    }
                }
            }, 0)

            musicBinding.seekBarPlaySong.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if(musicPlayer != null){
                        val currentTime = p1.toSongTime()
                        val maxDuration = musicPlayer?.duration

                        musicBinding.tvStartTime.text = currentTime

                        if(p1 == maxDuration){
                            playNextSong()
                        }

                        if(!musicPlayer?.isPlaying!!){
                            musicBinding.tvStartTime.text =musicPlayer?.currentPosition?.toSongTime()
                        }

                        if(p2){
                            musicPlayer?.seekTo(p1)
                            p0?.progress = p1
                        }
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    TODO("Not yet implemented")
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    TODO("Not yet implemented")
                }
            })

        }catch (e : IllegalStateException){
            Log.e("Music Playing", "playSong:  ${e.message}", )
        }
    }

    private fun checkMusicButton(){
        if(musicPlayer?.isPlaying!!){
            musicBinding.btnPlaySong.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_pause_circle_filled_80))
        }else{
            musicBinding.btnPlaySong.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_play_circle_80))
        }
    }

    private fun onClick(){
        musicBinding.tbPlaySong.setNavigationOnClickListener {
            finish()
        }

        musicBinding.btnPrevSong.setOnClickListener {
            playPrevSong()
        }

        musicBinding.btnPlaySong.setOnClickListener {
            if(musicPlayer?.isPlaying!!){
                musicPlayer?.pause()
                musicBinding.btnPlaySong.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_play_circle_80))
            }else{
                musicPlayer?.start()
                musicBinding.btnPlaySong.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_pause_circle_filled_80))
            }
        }

        musicBinding.btnNextSong.setOnClickListener {
            playNextSong()
        }
    }
}
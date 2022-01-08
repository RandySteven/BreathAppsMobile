package com.example.breathingapps2.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.breathingapps2.R
import com.example.breathingapps2.VideoActivity
import com.example.breathingapps2.adapter.VideosAdapter
import com.example.breathingapps2.databinding.FragmentVideoRelaxationBinding
import com.example.breathingapps2.models.Song
import com.example.breathingapps2.models.Video
import com.example.musicapps.utils.hide
import com.example.musicapps.utils.visible
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.startActivity

class VideoFragment : Fragment(){

    private var _binding : FragmentVideoRelaxationBinding ?= null
    private val videoBinding get() = _binding
    private lateinit var videosAdapter : VideosAdapter
    private lateinit var databaseVideos : DatabaseReference

    private val eventListenerVideos = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            hideLoading()
            val gson = Gson().toJson(snapshot.value)
            val type = object : TypeToken<MutableList<Video>>(){}.type

            val videos = Gson().fromJson<MutableList<Video>>(gson, type)

            if(videos != null){
                videosAdapter.setData(videos)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideoRelaxationBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videosAdapter = VideosAdapter()
        databaseVideos = FirebaseDatabase.getInstance().getReference("videos")

        swipeVideo()
        onClick()

        showLoading()
        showVideos()

        Handler(Looper.getMainLooper()).postDelayed({
            hideLoading()
        },1000)
    }

    private fun showVideos(){
        databaseVideos.addValueEventListener(eventListenerVideos)

        videoBinding?.rvVideos?.adapter = videosAdapter
    }

    private fun swipeVideo(){
        videoBinding?.swpVideo?.setOnRefreshListener {
            showVideos()
        }
    }

    private fun hideLoading(){
        videoBinding?.swpVideo?.hide()
    }

    private fun showLoading(){
        videoBinding?.swpVideo?.visible()
    }

    private fun onClick(){
        videosAdapter?.onClick { videos, position ->
            context?.startActivity<VideoActivity>(
                VideoActivity.KEY_VIDEOS to videos,
                VideoActivity.KEY_POSITION to position
            )
        }

        videoBinding?.backArrow?.setOnClickListener {
            changeFragment(HomeFragment(), "home")
        }
    }

    private fun changeFragment(fragment: Fragment, tag :String){
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_main, fragment, tag)
            ?.commit()
    }
}
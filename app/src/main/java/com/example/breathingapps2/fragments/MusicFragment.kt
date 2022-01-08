package com.example.breathingapps2.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breathingapps2.MusicActivity
import com.example.breathingapps2.R
import com.example.breathingapps2.adapter.MusicsAdapter
import com.example.breathingapps2.databinding.FragmentMusicRelaxationBinding
import com.example.breathingapps2.models.Song
import com.example.musicapps.utils.gone
import com.example.musicapps.utils.hide
import com.example.musicapps.utils.visible
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.startActivity

class MusicFragment : Fragment() {

    private var _binding : FragmentMusicRelaxationBinding ?= null
    private val musicRelaxationBinding get() = _binding
    private lateinit var musicsAdapter: MusicsAdapter
    private lateinit var databaseSongs : DatabaseReference

    private val eventListenerSongs = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            hideLoading()
            val gson = Gson().toJson(snapshot.value)
            val type = object : TypeToken<MutableList<Song>>(){}.type

            val songs = Gson().fromJson<MutableList<Song>>(gson, type)

            if(songs != null){
                musicsAdapter.setData(songs)
            }
        }

        override fun onCancelled(error: DatabaseError) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMusicRelaxationBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        musicsAdapter = MusicsAdapter()
        databaseSongs = FirebaseDatabase.getInstance().getReference("songs")

        swipeSongs()
        onClick()

        showLoading()
        showSongs()

        Handler(Looper.getMainLooper()).postDelayed({
        }, 2000)
    }

    private fun onClick(){
        musicsAdapter.onClick{ songs,position ->
            context?.startActivity<MusicActivity>(
                MusicActivity.KEY_SONGS to songs,
                MusicActivity.KEY_POSITION to position
            )
        }
    }

    private fun swipeSongs(){
        musicRelaxationBinding?.swpMusic?.setOnRefreshListener {
            showSongs()
        }
    }

    private fun showSongs(){
        databaseSongs.addValueEventListener(eventListenerSongs)

        musicRelaxationBinding?.rvMusics?.adapter = musicsAdapter
    }

    private fun hideLoading(){
        musicRelaxationBinding?.swpMusic?.hide()
    }

    private fun showLoading(){
        musicRelaxationBinding?.swpMusic?.visible()
    }

}
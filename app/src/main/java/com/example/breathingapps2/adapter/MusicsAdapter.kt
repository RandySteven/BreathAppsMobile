package com.example.breathingapps2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.breathingapps2.databinding.ListMusicBinding
import com.example.breathingapps2.databinding.MusicDetailBinding
import com.example.breathingapps2.models.Song

class MusicsAdapter : RecyclerView.Adapter<MusicsAdapter.ViewHolder>() {

    private var songs = mutableListOf<Song>()
    private var listener : ((MutableList<Song>, Int) -> Unit) ?= null

    inner class ViewHolder(private val binding : ListMusicBinding) : RecyclerView.ViewHolder
        (binding.root){
        fun bindItem(song : Song, listener: ((MutableList<Song>, Int) -> Unit)?){
            binding?.tvNameSong.text = song.nameSong

            itemView.setOnClickListener {
                if(listener != null){
                    listener(songs, adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicsAdapter.ViewHolder {
        val binding = ListMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicsAdapter.ViewHolder, position: Int) {
        holder.bindItem(songs[position], listener)
    }

    override fun getItemCount(): Int = songs.size

    fun onClick(listener: ((MutableList<Song>, Int) -> Unit)?){
        this.listener = listener
    }

    fun setData(songs : MutableList<Song>){
        this.songs = songs
        notifyDataSetChanged()
    }
}
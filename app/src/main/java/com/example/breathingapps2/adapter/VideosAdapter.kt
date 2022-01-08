package com.example.breathingapps2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.breathingapps2.databinding.ListVideoBinding
import com.example.breathingapps2.models.Video

class VideosAdapter : RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    private var videos = mutableListOf<Video>()
    private var listener : ((MutableList<Video>, Int) -> Unit) ?= null

    inner class ViewHolder(private val binding : ListVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(video : Video, listener : ((MutableList<Video>, Int) -> Unit)?){
            binding?.tvVideoTitle.text = video.videoTitle

            itemView.setOnClickListener {
                if(listener != null){
                    listener(videos, adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(videos[position], listener)
    }

    override fun getItemCount(): Int = videos.size

    fun setData(videos : MutableList<Video>){
        this.videos = videos
        notifyDataSetChanged()
    }

    fun onClick(listener: ((MutableList<Video>, Int) -> Unit)?){
        this.listener = listener
    }
}
package com.example.breathingapps2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.breathingapps2.databinding.ListCustomBinding
import com.example.breathingapps2.models.Interval

class IntervalsAdapter : RecyclerView.Adapter<IntervalsAdapter.ViewHolder>() {

    private var intervals = mutableListOf<Interval>()
    private var listener : ((MutableList<Interval>, Int) -> Unit) ?= null

    inner class ViewHolder(private val binding : ListCustomBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bindItem(interval: Interval, listener : ((MutableList<Interval>, Int) -> Unit)?){
            binding?.tvSavedInterval.text = "Saved interval ${interval.name}"

            itemView.setOnClickListener {
                if(listener != null){
                    listener(intervals, adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListCustomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(intervals[position], listener)
    }

    override fun getItemCount(): Int = intervals.size

    fun setData(intervals : MutableList<Interval>){
        this.intervals = intervals
        notifyDataSetChanged()
    }

    fun onClick(listener: ((MutableList<Interval>, Int) -> Unit)?){
        this.listener = listener
    }
}
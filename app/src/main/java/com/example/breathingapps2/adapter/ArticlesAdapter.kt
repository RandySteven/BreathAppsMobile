package com.example.breathingapps2.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.breathingapps2.databinding.ListArticleBinding
import com.example.breathingapps2.models.Article
import com.example.breathingapps2.models.Song

class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    private var articles = mutableListOf<Article>()
    private var listener : ((MutableList<Article>, Int) -> Unit) ?= null
    private var image : Bitmap ?= null

    inner class ViewHolder(private val binding : ListArticleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindItem(article: Article, listener : ((MutableList<Article>, Int) -> Unit)?){
            binding?.articleTitle.text = article.title
            Glide.with(binding?.root)
                .load(article.image)
                .placeholder(android.R.color.darker_gray)
                .into(binding?.articleImage)


            itemView.setOnClickListener {
                if(listener != null){
                    listener(articles, adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(articles[position], listener)
    }

    override fun getItemCount(): Int = articles.size

    fun setData(articles : MutableList<Article>){
        this.articles = articles
        notifyDataSetChanged()
    }

    fun onClick(listener: ((MutableList<Article>, Int) -> Unit)?){
        this.listener = listener
    }

}
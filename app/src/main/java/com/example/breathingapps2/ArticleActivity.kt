package com.example.breathingapps2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.breathingapps2.databinding.ActivityDetailArticleBinding
import com.example.breathingapps2.models.Article
import org.jetbrains.anko.startActivity

class ArticleActivity : AppCompatActivity() {

    companion object{
        const val KEY_ARTICLES = "key_articles"
        const val KEY_POSITION = "key_position"
    }

    private lateinit var articleBinding : ActivityDetailArticleBinding
    private var articles : MutableList<Article> ?= null
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        articleBinding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(articleBinding?.root)

        getData()
        onClick()
    }

    private fun onClick(){
        articleBinding?.backArrow?.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
    }

    private fun getData(){
        if(intent != null){
            articles = intent.getParcelableArrayListExtra(KEY_ARTICLES)
            position = intent.getIntExtra(KEY_POSITION, 0)
            articles?.let{
                val article = it[position]
                initView(article)
            }
        }
    }

    private fun initView(article : Article){
        articleBinding?.detailTitle.text = article.title
        articleBinding?.detailDescription.text = article.body

        Glide.with(this)
            .load(article.image)
            .placeholder(android.R.color.darker_gray)
            .into(articleBinding.articleImage)
    }
}
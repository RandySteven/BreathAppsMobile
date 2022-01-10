package com.example.breathingapps2.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.example.breathingapps2.ArticleActivity
import com.example.breathingapps2.R
import com.example.breathingapps2.adapter.ArticlesAdapter
import com.example.breathingapps2.api.ApiClient
import com.example.breathingapps2.databinding.FragmentArticleBinding
import com.example.breathingapps2.datamodels.GetArticleResponse
import com.example.breathingapps2.models.Article
import com.example.musicapps.utils.hide
import com.example.musicapps.utils.visible
import com.google.gson.Gson
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleFragment : Fragment() {
    private var _binding : FragmentArticleBinding ?= null
    private val articleBinding get() = _binding
    private lateinit var articlesAdapter : ArticlesAdapter
    private var articles : MutableList<Article> = mutableListOf<Article>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onPause() {
        super.onPause()
        notification(articles)
    }

    override fun onDestroy() {
        super.onDestroy()
        notification(articles)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articlesAdapter = ArticlesAdapter()

        swipeArticle()
        initArticle()
        onClick()

        showLoading()
        loadData()
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoading()
        }, 2000)
    }

    private fun initArticle(){
        articleBinding?.rvArticles?.adapter = articlesAdapter
    }

    private fun onClick(){
        articlesAdapter.onClick{ articles, position ->
            context?.startActivity<ArticleActivity>(
                ArticleActivity.KEY_ARTICLES to articles,
                ArticleActivity.KEY_POSITION to position
            )
        }
    }

    private fun swipeArticle(){
        articleBinding?.swpArticle?.setOnRefreshListener {
            loadData()
        }
    }

    private fun hideLoading(){
        articleBinding?.swpArticle?.hide()
    }

    private fun showLoading(){
        articleBinding?.swpArticle?.visible()
    }

    private fun notification(articles : MutableList<Article>){
        loadData()
        var size : Int = (0 until articles?.size-1).random()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var channel : NotificationChannel = NotificationChannel("Add Data", "Add new intervals", NotificationManager.IMPORTANCE_DEFAULT)
            var manager : NotificationManager? = context?.getSystemService(NotificationManager::class.java)

            manager?.createNotificationChannel(channel)
        }

        var builder : NotificationCompat.Builder = NotificationCompat.Builder(context?.applicationContext!!, "n")
            .setContentTitle(articles[size]?.title)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setAutoCancel(false)
            .setContentText(articles[size]?.body)

        var managerCompat : NotificationManagerCompat = NotificationManagerCompat.from(context?.applicationContext!!)
        managerCompat.notify(999, builder.build())

        size = 0
    }

    private fun loadData(){
        ApiClient.apiService.getPostData().enqueue(object : Callback<GetArticleResponse>{
            override fun onResponse(
                call: Call<GetArticleResponse>,
                response: Response<GetArticleResponse>
            ) {
                if(response.isSuccessful){
                    val result = response.body()?.posts
                    val list = ArrayList<Article>()
                    if(!result.isNullOrEmpty()){
                        Log.d("TAG", "onResponse: " + Gson().toJson(result))
                        result.forEach { articleItemResponse ->
                            val article = Article(
                            articleItemResponse.title,
                            articleItemResponse.body,
                            articleItemResponse.image)
                            println(article.title)
                            list.add(article)
                        }
                        articlesAdapter.setData(list)
                        articles = list
                    }else{
                        println("No Data")
                    }
                }
            }

            override fun onFailure(call: Call<GetArticleResponse>, t: Throwable) {
                t.printStackTrace()
                println("Gagal panggi API")
            }
        })
    }
}
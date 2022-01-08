package com.example.breathingapps2.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.breathingapps2.ArticleActivity
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return _binding?.root
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
                    }else{
                        println("No Data")
                    }
                }
            }

            override fun onFailure(call: Call<GetArticleResponse>, t: Throwable) {
                t.printStackTrace()
                println("Gagal panggi lapi")
            }
        })
    }
}
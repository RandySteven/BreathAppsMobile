package com.example.breathingapps2.api

import com.example.breathingapps2.datamodels.GetArticleResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("v1/posts")
    fun getPostData() : Call<GetArticleResponse>
}
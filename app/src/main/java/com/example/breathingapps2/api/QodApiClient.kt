package com.example.breathingapps2.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QodApiClient {
    private const val BASE_URL = "https://quotes.rest"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService = retrofit.create(QodApiService::class.java)
}
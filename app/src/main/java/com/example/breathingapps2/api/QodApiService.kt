package com.example.breathingapps2.api

import com.example.breathingapps2.models.Qod
import com.example.breathingapps2.models.Quote
import retrofit2.Call
import retrofit2.http.GET

interface QodApiService {
    @GET("/qod.json")
    fun getQod() : Call<Qod>
}
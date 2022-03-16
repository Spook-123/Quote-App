package com.example.quotes_app.data

import retrofit2.Call
import retrofit2.http.GET

interface QuoteService {

    @GET("random")
    fun getData(): Call<QuoteData>
}
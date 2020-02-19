package com.example.myapplication.api.rest

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {
    companion object {
        fun getInstance(): Retrofit? = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://data.metromobilite.fr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
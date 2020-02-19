package com.example.recyclerviewexemple.api.rest

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {
    companion object {
        fun getInstance(): Retrofit? {
            val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient: OkHttpClient =
                OkHttpClient.Builder().addInterceptor(interceptor).build()
            return Retrofit.Builder()
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://data.metromobilite.fr/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
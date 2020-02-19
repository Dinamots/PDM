package com.example.recyclerviewexemple.api.rest

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {
    companion object {

        /* val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.le-systeme-solaire.net/rest.php/")
            .build()

         retrofit.create(RestApi::class.java) */
    }
}
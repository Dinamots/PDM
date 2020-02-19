package com.example.recyclerviewexemple.api.rest

import fr.clerc.myapplication.kotlin.model.FeatureCollection
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable

interface MetromobiliteService {
    @GET("/api/findType/json")
    fun getPointsVente(@Query("types") types: String, @Query("query") query: String): Observable<List<FeatureCollection>>
}
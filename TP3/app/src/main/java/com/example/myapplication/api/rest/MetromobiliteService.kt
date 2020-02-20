package com.example.myapplication.api.rest

import fr.clerc.myapplication.kotlin.model.FeatureCollection
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MetromobiliteService {
    @GET("/api/findType/json")
    fun getPointsVente(@Query("types") types: String, @Query("query") query: String?): Observable<FeatureCollection>
}
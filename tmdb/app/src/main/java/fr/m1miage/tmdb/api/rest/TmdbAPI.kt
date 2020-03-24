package fr.m1miage.tmdb.api.rest

import fr.m1miage.tmdb.api.model.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    @GET("movie/upcoming")
    fun getUpcoming(): Observable<Search<MovieResponse>>

    @GET("movie/top_rated")
    fun getTopRated(): Observable<Search<MovieResponse>>

    @GET("movie/popular")
    fun getPopular(): Observable<Search<MovieResponse>>

    @GET("movie/now_playing")
    fun getNowPlaying(): Observable<Search<MovieResponse>>

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String, @Query("page") page: Int): Observable<Search<MovieResponse>>

    @GET("movie/{id}")
    fun getMovie(@Path("id") id: Long): Observable<Movie>

    @GET("movie/{movie_id}/videos")
    fun getVideos(@Path("movie_id") id: Long) : Observable<Search<Video>>

    @GET("movie/{movie_id}/credits")
    fun getCredits(@Path("movie_id") id: Long): Observable<Credits>

}
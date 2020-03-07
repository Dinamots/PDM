package fr.m1miage.tmdb.api.rest

import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.api.model.Search
import io.reactivex.Observable
import retrofit2.http.GET
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

}
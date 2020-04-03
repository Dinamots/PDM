package fr.m1miage.tmdb.utils.extension

import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.utils.FAVORITES_SHARED_KEY
import fr.m1miage.tmdb.utils.Favorites
import fr.m1miage.tmdb.utils.MOVIE_MAP_SHARED_KEY
import fr.m1miage.tmdb.utils.gson


fun SharedPreferences.getFavorites(): Favorites {
    return gson.fromJson(
        this.getString(
            FAVORITES_SHARED_KEY,
            gson.toJson(Favorites(mutableListOf()))
        ),
        Favorites::class.java
    )
}

fun SharedPreferences.isFavoriteMovie(movie: MovieResponse): Boolean {
    val favorites = this.getFavorites()
    return favorites.movies.find { it.id == movie.id } != null
}

fun SharedPreferences.addOrRemoveMovie(movie: MovieResponse) {
    var favorites: Favorites = this.getFavorites()
    val pref = this
    with(this.edit()) {
        favorites = pref.editFavorites(favorites, movie)
        println(favorites)
        this?.putString(
            FAVORITES_SHARED_KEY,
            gson.toJson(favorites)
        )
        println(favorites.movies)
        this?.apply()
    }
}

internal fun SharedPreferences.editFavorites(
    favorites: Favorites,
    movie: MovieResponse
): Favorites {
    val deletableMovie: MovieResponse? = favorites.movies.find { it.id == movie.id }
    if (deletableMovie == null) {
        favorites.movies.add(movie)
    } else {
        favorites.movies = (favorites.movies.filter { it.id != movie.id }).toMutableList()
    }
    return favorites
}

fun SharedPreferences.getMovieMap(): MutableMap<String, MutableList<MovieResponse>> {
    return gson.fromJson(
        getString(
            MOVIE_MAP_SHARED_KEY,
            gson.toJson(mutableMapOf<String, MutableList<MovieResponse>>())
        ),
        object : TypeToken<MutableMap<String, MutableList<MovieResponse>>>() {}.type
    )
}

fun SharedPreferences.addMovieList(movies: List<MovieResponse>, key: String) {
    val map = this.getMovieMap()
    with(this.edit()) {
        map[key] = movies.toMutableList()
        this.putString(MOVIE_MAP_SHARED_KEY, gson.toJson(map))
        this?.apply()
    }
}
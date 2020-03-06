package fr.m1miage.tmdb.utils.extension

import android.content.SharedPreferences
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.utils.FAVORITES_SHARED_KEY
import fr.m1miage.tmdb.utils.Favorites
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

fun SharedPreferences.isFavoriteMovie(movieId: Int): Boolean {
    val favorites = this.getFavorites()
    return favorites.movies.find { it.id == movieId } != null
}

fun SharedPreferences.addOrRemoveMovie(movie: MovieResponse) {
    val favorites: Favorites = this.getFavorites()
    val pref = this
    with(this.edit()) {
        pref.editFavorites(favorites, movie)
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
) {
    if (favorites.movies.find { it.id == movie.id } == null) {
        favorites.movies.add(movie)
    } else {
        favorites.movies.remove(movie)
    }
}

package fr.m1miage.tmdb.utils.extension

import android.content.SharedPreferences
import fr.m1miage.tmdb.api.model.Movie
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
       favorites.movies.remove(deletableMovie)
    }
    return favorites
}

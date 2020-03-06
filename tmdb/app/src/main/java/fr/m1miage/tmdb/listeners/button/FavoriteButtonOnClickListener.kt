package fr.m1miage.tmdb.listeners.button

import android.content.SharedPreferences
import android.view.View
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.utils.FAVORITES_SHARED_KEY
import fr.m1miage.tmdb.utils.Favorites
import fr.m1miage.tmdb.utils.getFavorites
import fr.m1miage.tmdb.utils.gson

class FavoriteButtonOnClickListener(
    val preferences: SharedPreferences,
    val movie: MovieResponse
) : View.OnClickListener {
    override fun onClick(v: View?) {
        onFavoriteButtonClick(movie)
    }

    private fun onFavoriteButtonClick(movie: MovieResponse) {
        with(preferences.edit()) {
            val favorites: Favorites = getFavorites(preferences)
            editFavorites(favorites, movie.id)
            this?.putString(
                FAVORITES_SHARED_KEY,
                gson.toJson(favorites)
            )
            println(favorites.movieIds)
            this?.apply()
        }
    }

    private fun editFavorites(
        favorites: Favorites,
        movieId: Int
    ) {
        if (favorites.movieIds.find { it == movieId } == null) {
            favorites.movieIds.add(movieId)
        } else {
            favorites.movieIds.remove(movieId)
        }
    }


}
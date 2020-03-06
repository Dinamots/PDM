package fr.m1miage.tmdb.listeners.button

import android.content.SharedPreferences
import android.view.View
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.utils.FAVORITES_SHARED_KEY
import fr.m1miage.tmdb.utils.Favorites
import fr.m1miage.tmdb.utils.extension.addOrRemoveMovie
import fr.m1miage.tmdb.utils.extension.getFavorites
import fr.m1miage.tmdb.utils.gson

class FavoriteButtonOnClickListener(
    val preferences: SharedPreferences,
    val movie: MovieResponse
) : View.OnClickListener {
    override fun onClick(v: View?) {
        onFavoriteButtonClick(movie)
    }

    private fun onFavoriteButtonClick(movie: MovieResponse) {
        preferences.addOrRemoveMovie(movie)

    }


}
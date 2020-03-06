package fr.m1miage.tmdb

import android.content.SharedPreferences
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.utils.extension.addOrRemoveMovie
import fr.m1miage.tmdb.utils.extension.isFavoriteMovie

class FavoriteMovieAdapter(
    movies: MutableList<MovieResponse>, headerStr: String?,
    val preferences: SharedPreferences,
    listener: (MovieResponse) -> Unit,
    favoriteListener: (MovieResponse, MovieAdapter) -> Unit
) : MovieAdapter(
    movies, headerStr,
    preferences, listener, favoriteListener
) {
    override fun initItemViewHolder(position: Int, holder: ItemViewHolder) {

        val movie = movies[position]
        super.initItemViewHolder(position, holder)

    }

}
package fr.m1miage.tmdb.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.api.model.MovieResponse

const val API_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "ec254d6e8ac3b46e270afe8da4bb0d5d"
const val TMDB_IMAGES_PATH = "https://image.tmdb.org/t/p/original"
const val FAVORITES_SHARED_KEY = "FAVORITES"
val gson = Gson()
data class Favorites (
     var movies: MutableList<MovieResponse>
)


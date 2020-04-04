package fr.m1miage.tmdb.utils

import android.os.Build
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.google.gson.*
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.api.model.MovieResponse
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val API_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "ec254d6e8ac3b46e270afe8da4bb0d5d"
const val GOOFLE_API_KEY = "AIzaSyArQzig__mI5KngXeX5Zire4RZl74X739U"
const val TMDB_IMAGES_PATH = "https://image.tmdb.org/t/p/original"
const val FAVORITES_SHARED_KEY = "FAVORITES"
const val MOVIE_MAP_SHARED_KEY = "MOVIES"
const val MOVIE_MAP_UPCOMING_KEY = "UPCOMING"
const val MOVIE_MAP_TOP_KEY = "TOP"
const val MOVIE_MAP_POPULAR_KEY = "POPULAR"
const val MOVIE_MAP_PLAYING_KEY = "PLAYING"

const val IMDB_MOVIE_PATH = "https://www.imdb.com/title/"
const val ANONYMOUS_IMG_PATH =
    "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png"
const val DEFAULT_MOVIE_IMG_PATH =
    "https://cdn.pixabay.com/photo/2017/11/21/22/51/award-2969422_960_720.jpg"
const val IMDB_PERSON_PATH = "https://www.imdb.com/name/"
const val MAX_SPAN_COUNT = 3
const val MIN_SPAN_COUNT = 1
const val ERROR_OCCURED_MSG = "An error as occured, please retry"

data class Favorites(
    var movies: MutableList<MovieResponse>
)

val gson = GsonBuilder().registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date?> {
    var df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date? {
        return try {
            df.parse(json?.asString!!)
        } catch (e: ParseException) {
            null
        }
    }

}).serializeNulls().create()

fun formatDate(date: Date) = SimpleDateFormat("dd-MM-yyyy").format(date)

fun snack(view: View, msg: String) {
    val snackbar: Snackbar = Snackbar
        .make(view, msg, Snackbar.LENGTH_LONG)
    snackbar.show()
}

fun reload(manager: FragmentManager, fragment: Fragment) {
    val ft: FragmentTransaction = manager.beginTransaction()
    if (Build.VERSION.SDK_INT >= 26) {
        ft.setReorderingAllowed(false)
    }
    ft.detach(fragment).attach(fragment).commit()
}
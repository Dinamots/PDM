package fr.m1miage.tmdb.utils

import com.google.gson.*
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
const val MOVIE_ID_EXTRAS_KEY = "MOVIE_ID"
data class Favorites (
     var movies: MutableList<MovieResponse>
)

val gson =  GsonBuilder().registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date?> {
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
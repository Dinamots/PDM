package fr.m1miage.tmdb.api.model

import java.io.Serializable
import java.util.*
import kotlin.Int

data class Movie (
    val adult: Boolean?,
    val backdrop_path: String?,
    val belongs_to_collection: Any?,
    val budget: Long?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val production_companies: List<Company>?,
    val production_countries: List<Country>?,
    val release_date: Date?,
    val revenue: Long?,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>?,
    val status: String?,
    val title: String?,
    val tagline: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
) : Serializable

data class Genre(
    val id: Int,
    val name: String
)

data class Company(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)

data class Country(
    val iso_3166_1: String,
    val name: String
)

data class SpokenLanguage(
    val iso_639_1: String,
    val name: String
)

data class Video(
    val id: String,
    val iso_639_1: String,
    val iso_3166_1: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)

package fr.m1miage.tmdb.api.model

import java.io.Serializable
import java.util.*

data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<Company>,
    val production_countries: List<Country>,
    val release_date: Date,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
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
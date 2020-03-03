package fr.m1miage.tmdb.api.model

import java.io.Serializable

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
    val production_companies: List<Company>
) : Serializable

data class Genre(
    val id: Int,
    val name: String
) : Serializable

data class Company(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
) : Serializable
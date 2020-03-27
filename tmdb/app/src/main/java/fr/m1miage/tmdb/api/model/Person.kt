package fr.m1miage.tmdb.api.model

import java.util.*

data class Person(
    val cast_id: Int?,
    val department: String?,

    val job: String?,
    val order: Int?,
    val id: Int,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val name: String,
    val profile_path: String?
)

data class PersonDetail(
    val birthday: Date?,
    val known_for_department: String,
    val deathday: Date?,
    val id: Int,
    val name: String,
    val also_known_as: List<String>,
    val gender: Int,
    val biography: String,
    val popularity: Double,
    val place_of_birth: String,
    val profile_path: String?,
    val adult: Boolean,
    val imdb_id: String,
    val homepage: String
)

data class Credits<T>(
    val id: Int,
    val cast: MutableList<T>,
    val crew: MutableList<T>
)
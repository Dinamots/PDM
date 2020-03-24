package fr.m1miage.tmdb.api.model

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

data class Credits(
    val id: Int,
    val cast: List<Person>,
    val crew: List<Person>
)
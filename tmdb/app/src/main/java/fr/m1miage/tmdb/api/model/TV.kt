package fr.m1miage.tmdb.api.model

data class TVResponse(
    val poster_path: String?,
    val popularity: Int?,
    val id: Int?,
    val overview: String?,
    val backdrop_path: String?,
    val vote_average: Int?,
    val media_type: String,
    val first_air_date: String,
    val origin_country: List<String>?,
    val genre_ids: List<Int>?,
    val original_language: String?,
    val vote_count: Int?,
    val name: String?,
    val original_name: String?
)
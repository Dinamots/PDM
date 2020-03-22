package fr.m1miage.tmdb.api.model

import java.io.Serializable

data class Search<T>(
    val id: Int?,
    val page: Int?,
    val total_results: Int?,
    val total_pages: Int?,
    val results: List<T>
) : Serializable
package fr.m1miage.tmdb.utils.extension

import fr.m1miage.tmdb.api.model.Credits
import fr.m1miage.tmdb.api.model.MovieResponse

fun Credits<MovieResponse>.getMovieResponseList(): MutableList<MovieResponse> = run {
    val copy = this.copy()
    copy.cast.addAll(this.crew)
    var movies = copy.cast.distinctBy {it.id}
    movies = movies.sortedBy { it.title }
    return movies.toMutableList()
}

package fr.m1miage.tmdb.utils.extension

import fr.m1miage.tmdb.api.model.Genre
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.utils.DEFAULT_MOVIE_IMG_PATH
import fr.m1miage.tmdb.utils.TMDB_IMAGES_PATH

fun Movie.toMovieReponse(): MovieResponse {
    return MovieResponse(
        popularity = popularity,
        vote_count = vote_count,
        video = video,
        poster_path = poster_path,
        id = id,
        adult = adult,
        backdrop_path = backdrop_path,
        original_language = original_language,
        original_title = original_title,
        genre_ids = genres.fold(mutableListOf(), { acc, genre ->
            run {
                acc.add(genre.id)
                acc
            }
        }),
        vote_average = vote_average,
        overview = overview,
        release_date = release_date,
        title = title
    )
}

fun Movie.getImgPath() =
    if (this.poster_path == null) DEFAULT_MOVIE_IMG_PATH else TMDB_IMAGES_PATH + this.poster_path
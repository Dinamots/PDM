package fr.m1miage.tmdb.ui.movie

import androidx.lifecycle.MutableLiveData
import fr.m1miage.tmdb.FetchViewModel
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.api.model.Video
import io.reactivex.android.schedulers.AndroidSchedulers

class MovieDetailViewModel : FetchViewModel() {
    val movieId = MutableLiveData<Int>()
    val movieIds = mutableListOf<Int>()

    var movie = MutableLiveData<Movie>()
    var videos = MutableLiveData<List<Video>>()
    val onLoadingMovie = MutableLiveData<Boolean>()
    val onLoadingVideos = MutableLiveData<Boolean>()
    val onErrorMovie = MutableLiveData<Boolean>()
    val onVideosError = MutableLiveData<Boolean>()

    fun fetchMovie(id: Int) {
        fetchData(
            RetrofitManager.tmdbAPI.getMovie(id.toLong()),
            movie,
            onLoadingMovie,
            onErrorMovie
        )
    }

    fun fetchVideos(id: Int) {
        fetchSearch(
            RetrofitManager.tmdbAPI.getVideos(id.toLong()),
            videos,
            null,
            onLoadingVideos,
            onVideosError
        )
    }

    fun fetchAll(id: Int) {
        fetchVideos(id)
        fetchMovie(id)
    }

    fun getId(): Int? {
        val item = movieIds.lastOrNull()
        if (movieIds.isNotEmpty()) {
            movieIds.removeAt(movieIds.size - 1)
        }
        return item
    }

    fun storeId() {
        if(movieId.value != null) {
            movieIds.add(movieId.value!!)
        }
    }


}

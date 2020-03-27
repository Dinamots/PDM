package fr.m1miage.tmdb.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.api.model.Search
import fr.m1miage.tmdb.api.model.Video
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class MovieDetailViewModel : ViewModel() {
    val movieId = MutableLiveData<Int>()
    var movie = MutableLiveData<Movie>()
    var videos = MutableLiveData<List<Video>>()
    val isLoading = MutableLiveData<Boolean>()

    fun fetchMovie(id: Int) {
        isLoading.postValue(true)
        val disposable =
            RetrofitManager.tmdbAPI.getMovie(id.toLong())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { mv -> movie.postValue(mv); isLoading.postValue(false) },
                    { error -> error.printStackTrace()}
                )
    }

    fun fetchVideos(id: Int) {
        val disposable = RetrofitManager.tmdbAPI.getVideos(id.toLong())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { search -> videos.postValue(search.results) },
                { error -> error.printStackTrace() }
            )
    }

    fun fetchAll(id: Int) {
        fetchVideos(id)
        fetchMovie(id)
    }

}

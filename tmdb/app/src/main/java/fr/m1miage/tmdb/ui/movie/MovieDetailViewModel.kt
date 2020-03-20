package fr.m1miage.tmdb.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.api.model.Search
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class MovieDetailViewModel : ViewModel() {
    val movieId = MutableLiveData<Int>()
    fun movie(): LiveData<Movie> = movie

    private val movie by lazy {
        getLiveData(RetrofitManager.tmdbAPI.getMovie(movieId.value?.toLong()!!))
    }

    private fun getLiveData(observable: Observable<Movie>): MutableLiveData<Movie> {
        val liveData = MutableLiveData<Movie>()
        liveData.also {
            loadMovies(liveData, observable)
        }
        return liveData
    }

    private fun loadMovies(liveData: MutableLiveData<Movie>, observable: Observable<Movie>) {
        val disposable = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res -> liveData.value = res }, { error -> println(error) })
    }


}

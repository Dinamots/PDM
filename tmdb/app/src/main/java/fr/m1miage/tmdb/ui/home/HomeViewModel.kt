package fr.m1miage.tmdb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.api.model.Search
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class HomeViewModel : ViewModel() {

    private val upcomingMovies by lazy {

        getLiveData(RetrofitManager.tmdbAPI.getUpcoming())
    }

    fun getUpcomingMovies(): LiveData<List<MovieResponse>> {
        return upcomingMovies
    }

    private val topRatedMovies by lazy {
        getLiveData(RetrofitManager.tmdbAPI.getTopRated())
    }

    fun getPopularMovies(): LiveData<List<MovieResponse>> {
        return popularMovies
    }

    private val popularMovies by lazy {
        getLiveData(RetrofitManager.tmdbAPI.getPopular())
    }

    fun getNowPlayingMovies(): LiveData<List<MovieResponse>> {
        return nowPlayingMovies
    }

    private val nowPlayingMovies by lazy {
        getLiveData(RetrofitManager.tmdbAPI.getNowPlaying())
    }

    fun getTopRatedMovies(): LiveData<List<MovieResponse>> {
        return topRatedMovies
    }

    private fun getLiveData(observable: Observable<Search<MovieResponse>>): MutableLiveData<List<MovieResponse>> {
        println("bonjour")
        val liveData = MutableLiveData<List<MovieResponse>>()
        liveData.also {
            loadMovies(liveData, observable)
        }
        return liveData
    }

    private fun loadMovies(
        liveData: MutableLiveData<List<MovieResponse>>,
        observable: Observable<Search<MovieResponse>>
    ) {

        val disposable = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res -> liveData.value = res.results }, { error -> println(error) })
    }

}
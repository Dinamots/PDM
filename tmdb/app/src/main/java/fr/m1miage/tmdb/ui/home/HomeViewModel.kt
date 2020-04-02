package fr.m1miage.tmdb.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.api.model.Search
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class HomeViewModel : ViewModel() {
    val upcomingMovies: MutableLiveData<List<MovieResponse>> = MutableLiveData()
    val topRatedMovies: MutableLiveData<List<MovieResponse>> = MutableLiveData()
    val popularMovies: MutableLiveData<List<MovieResponse>> = MutableLiveData()
    val nowPlayingMovies: MutableLiveData<List<MovieResponse>> = MutableLiveData()
    val onLoadUpcoming: MutableLiveData<Boolean> = MutableLiveData()
    val onLoadTopRated: MutableLiveData<Boolean> = MutableLiveData()
    val onLoadPopular: MutableLiveData<Boolean> = MutableLiveData()
    val onLoadNowPlaying: MutableLiveData<Boolean> = MutableLiveData()
    val onErrorUpcoming: MutableLiveData<Boolean> = MutableLiveData()
    val onErrorTopRated: MutableLiveData<Boolean> = MutableLiveData()
    val onErrorPopular: MutableLiveData<Boolean> = MutableLiveData()
    val onErrorNowPlaying: MutableLiveData<Boolean> = MutableLiveData()


    fun fetchUpcomingMovies() {
        fetchMovie(
            RetrofitManager.tmdbAPI.getUpcoming(),
            upcomingMovies,
            onLoadUpcoming,
            onErrorUpcoming
        )
    }

    fun fetchTopRatedMovies() {
        fetchMovie(
            RetrofitManager.tmdbAPI.getTopRated(),
            topRatedMovies,
            onLoadTopRated,
            onErrorTopRated
        )

    }

    fun fetchPopularMovies() {
        fetchMovie(
            RetrofitManager.tmdbAPI.getPopular(),
            popularMovies,
            onLoadPopular,
            onErrorPopular
        )
    }

    fun fetchNowPlayingMovies() {
        fetchMovie(
            RetrofitManager.tmdbAPI.getNowPlaying(),
            nowPlayingMovies,
            onLoadNowPlaying,
            onErrorNowPlaying
        )
    }

    fun fetchAll() {
        fetchNowPlayingMovies()
        fetchPopularMovies()
        fetchTopRatedMovies()
        fetchUpcomingMovies()
    }

    fun fetchMovie(
        observable: Observable<Search<MovieResponse>>,
        movies: MutableLiveData<List<MovieResponse>>,
        onLoad: MutableLiveData<Boolean>,
        onError: MutableLiveData<Boolean>
    ) {
        onLoad.postValue(true)
        val disposable = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { search -> movies.value = (search.results); onLoad.postValue(false) },
                { onError.postValue(true) }
            )
    }
}
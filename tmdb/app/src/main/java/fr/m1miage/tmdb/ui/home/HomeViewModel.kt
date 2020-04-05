package fr.m1miage.tmdb.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.FetchViewModel
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.api.model.Search
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class HomeViewModel : FetchViewModel() {
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
        fetchSearch(
            RetrofitManager.tmdbAPI.getUpcoming(),
            upcomingMovies,
            null,
            onLoadUpcoming,
            onErrorUpcoming
        )
    }

    fun fetchTopRatedMovies() {
        fetchSearch(
            RetrofitManager.tmdbAPI.getTopRated(),
            topRatedMovies,
            null,
            onLoadTopRated,
            onErrorTopRated
        )

    }

    fun fetchPopularMovies() {
        fetchSearch(
            RetrofitManager.tmdbAPI.getPopular(),
            popularMovies,
            null,
            onLoadPopular,
            onErrorPopular
        )
    }

    fun fetchNowPlayingMovies() {
        fetchSearch(
            RetrofitManager.tmdbAPI.getNowPlaying(),
            nowPlayingMovies,
            null,
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
}
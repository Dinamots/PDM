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

    fun fetchUpcomingMovies() {
        fetchMovie(RetrofitManager.tmdbAPI.getUpcoming(), upcomingMovies)
    }

    fun fetchTopRatedMovies() {
        fetchMovie(RetrofitManager.tmdbAPI.getTopRated(), topRatedMovies)

    }

    fun fetchPopularMovies() {
        fetchMovie(RetrofitManager.tmdbAPI.getPopular(), popularMovies)
    }

    fun fetchNowPlayingMovies() {
        fetchMovie(RetrofitManager.tmdbAPI.getNowPlaying(), nowPlayingMovies)
    }

    fun fetchAll() {
        fetchNowPlayingMovies()
        fetchPopularMovies()
        fetchTopRatedMovies()
        fetchUpcomingMovies()
    }

    fun fetchMovie(
        observable: Observable<Search<MovieResponse>>,
        movies: MutableLiveData<List<MovieResponse>>
    ) {

        val disposable = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { search -> movies.value = (search.results); println(search.results) },
                { err -> err.printStackTrace() }
            )
    }
}
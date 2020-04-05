package fr.m1miage.tmdb.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.FetchViewModel
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.api.model.MovieResponse
import io.reactivex.android.schedulers.AndroidSchedulers

class SearchViewModel : FetchViewModel() {
    val searchSting = MutableLiveData<String>()
    val movies = MutableLiveData<List<MovieResponse>>()
    val onLoadMovies = MutableLiveData<Boolean>()
    val onErrorMovies = MutableLiveData<Boolean>()
    val totalPages = MutableLiveData<Int>()

    fun fetchMovies(search: String, currentPage: Int) {
        fetchSearch(
            RetrofitManager.tmdbAPI.searchMovies(search, currentPage),
            movies,
            totalPages,
            onLoadMovies,
            onErrorMovies
        )
    }
}

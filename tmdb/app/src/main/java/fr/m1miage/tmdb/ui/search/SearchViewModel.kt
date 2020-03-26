package fr.m1miage.tmdb.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.api.model.MovieResponse
import io.reactivex.android.schedulers.AndroidSchedulers

class SearchViewModel : ViewModel() {
    val searchSting = MutableLiveData<String>()
    val movies = MutableLiveData<List<MovieResponse>>()
    val totalPages = MutableLiveData<Int>()

    fun fetchMovies(search: String, currentPage: Int) {
        val disposable = RetrofitManager.tmdbAPI.searchMovies(search, currentPage)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res ->
                    movies.postValue(res.results); totalPages.postValue(res.total_pages)
                },
                { error -> error.printStackTrace() }
            )
    }
}

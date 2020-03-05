package fr.m1miage.tmdb.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.api.model.MovieResponse

class MovieResponseViewModel: ViewModel() {
    private val upcomingMovies: MutableLiveData<List<MovieResponse>> by lazy {
        MutableLiveData<List<MovieResponse>>().also {
            loadUpcomingMovies()
        }
    }

    fun getUpComingMovies() = upcomingMovies

    private fun loadUpcomingMovies() {

    }

}
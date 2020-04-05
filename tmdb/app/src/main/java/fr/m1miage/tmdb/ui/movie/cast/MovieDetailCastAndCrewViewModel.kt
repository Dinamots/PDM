package fr.m1miage.tmdb.ui.movie.cast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.FetchViewModel
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Credits
import fr.m1miage.tmdb.api.model.Person
import io.reactivex.android.schedulers.AndroidSchedulers

class MovieDetailCastAndCrewViewModel : FetchViewModel() {
    val credits = MutableLiveData<Credits<Person>>()
    val onLoadingCredits = MutableLiveData<Boolean>()
    val onErrorCredits = MutableLiveData<Boolean>()

    fun fetchCredits(id: Int) {
        fetchData(
            RetrofitManager.tmdbAPI.getCredits(id.toLong()),
            credits,
            onLoadingCredits,
            onErrorCredits
        )
    }
}

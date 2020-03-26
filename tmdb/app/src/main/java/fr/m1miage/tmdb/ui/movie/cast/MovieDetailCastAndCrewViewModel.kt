package fr.m1miage.tmdb.ui.movie.cast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Credits
import fr.m1miage.tmdb.api.model.Person
import io.reactivex.android.schedulers.AndroidSchedulers

class MovieDetailCastAndCrewViewModel : ViewModel() {
    val credits = MutableLiveData<Credits<Person>>()

    fun fetchCredits(id: Int) {
        val disposable = RetrofitManager.tmdbAPI.getCredits(id.toLong())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { cds: Credits<Person> -> credits.postValue(cds) },
                { err -> println(err) }
            )
    }
}

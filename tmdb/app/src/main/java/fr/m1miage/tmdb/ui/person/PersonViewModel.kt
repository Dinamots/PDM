package fr.m1miage.tmdb.ui.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Credits
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.api.model.Person
import fr.m1miage.tmdb.api.model.PersonDetail
import io.reactivex.android.schedulers.AndroidSchedulers

class PersonViewModel : ViewModel() {
    val person: MutableLiveData<Person> = MutableLiveData<Person>()
    val personDetail: MutableLiveData<PersonDetail> = MutableLiveData<PersonDetail>()
    val filmography = MutableLiveData<Credits<MovieResponse>>()

    fun fecthPersonDetail(id: Int) {
        val disposable = RetrofitManager.tmdbAPI.getPerson(id.toLong())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { persDetail -> personDetail.postValue(persDetail) },
                { err -> println(err) }
            )
    }

    fun fetchFilmography(id: Int) {
        val disposable = RetrofitManager.tmdbAPI.getFilmography(id.toLong())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { fgy -> filmography.postValue(fgy) },
                { err -> println(err) }
            )
    }
}

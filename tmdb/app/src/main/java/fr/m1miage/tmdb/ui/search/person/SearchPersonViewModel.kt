package fr.m1miage.tmdb.ui.search.person

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Person
import fr.m1miage.tmdb.api.model.PersonResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer

class SearchPersonViewModel : ViewModel() {
    val persons: MutableLiveData<List<PersonResponse>> = MutableLiveData()
    val totalPages: MutableLiveData<Int> = MutableLiveData()
    fun fetchPersons(searchString: String, page: Int) {
        val disposable = RetrofitManager.tmdbAPI
            .searchPersons(searchString, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { search -> persons.postValue(search.results); totalPages.postValue(search.total_pages) },
                { err -> err.printStackTrace() }
            )
    }
}

package fr.m1miage.tmdb.ui.search.person

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.FetchViewModel
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Person
import fr.m1miage.tmdb.api.model.PersonResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer

class SearchPersonViewModel : FetchViewModel() {
    val persons: MutableLiveData<List<PersonResponse>> = MutableLiveData()
    val totalPages: MutableLiveData<Int> = MutableLiveData()
    val onLoadPersons = MutableLiveData<Boolean>()
    val onErrorPersons = MutableLiveData<Boolean>()
    fun fetchPersons(searchString: String, page: Int) {
        fetchSearch(
            RetrofitManager.tmdbAPI
                .searchPersons(searchString, page),
            persons,
            totalPages,
            onLoadPersons,
            onErrorPersons
        )
    }
}

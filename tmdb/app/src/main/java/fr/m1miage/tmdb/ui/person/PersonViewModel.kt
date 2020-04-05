package fr.m1miage.tmdb.ui.person

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.FetchViewModel
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class PersonViewModel : FetchViewModel() {
    val personIds = mutableListOf<Int>()
    val personId: MutableLiveData<Int> = MutableLiveData<Int>()
    val personDetail: MutableLiveData<PersonDetail> = MutableLiveData<PersonDetail>()
    val filmography = MutableLiveData<Credits<MovieResponse>>()
    val onLoadingPersonDetail = MutableLiveData<Boolean>()
    val onLoadingFilmography = MutableLiveData<Boolean>()
    val onErrorPersonDetail = MutableLiveData<Boolean>()
    val onErrorFilmography = MutableLiveData<Boolean>()

    fun fecthPersonDetail(id: Int) {
        fetchData(
            RetrofitManager.tmdbAPI.getPersonDetail(id.toLong()),
            personDetail,
            onLoadingPersonDetail,
            onErrorPersonDetail
        )
    }

    fun fetchFilmography(id: Int) {
        fetchData(
            RetrofitManager.tmdbAPI.getFilmography(id.toLong()),
            filmography,
            onLoadingFilmography,
            onErrorFilmography
        )
    }

    fun addId(id: Int) {
        personIds.add(id)
    }

    fun getId(): Int? {
        val item = personIds.lastOrNull()
        if (personIds.isNotEmpty()) {
            personIds.removeAt(personIds.size - 1)
        }
        return item
    }

    fun storeId() {
        if (personId.value != null) {
            personIds.add(personId.value!!)
        }
    }

    fun destroy() {
        personId.postValue(null)
        personDetail.postValue(null)
        filmography.postValue(null)

    }

}

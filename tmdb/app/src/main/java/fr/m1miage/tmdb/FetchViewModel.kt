package fr.m1miage.tmdb

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import fr.m1miage.tmdb.api.model.Search
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers


abstract class FetchViewModel : ViewModel() {
    fun <T> fetchSearch(
        observable: Observable<Search<T>>,
        liveData: MutableLiveData<List<T>>,
        pages: MutableLiveData<Int>?,
        loading: MutableLiveData<Boolean>,
        error: MutableLiveData<Boolean>
    ) {
        val dispoable = observable.observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { search ->
                    liveData.postValue(search.results)
                    pages?.postValue(search.total_pages)
                    loading.postValue(true)
                },
                { error.postValue(true) }
            )
    }

    fun <T> fetchData(
        observable: Observable<T>,
        liveData: MutableLiveData<T>,
        loading: MutableLiveData<Boolean>,
        error: MutableLiveData<Boolean>
    ) {
        val dispoable = observable.observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->
                    liveData.postValue(data)
                    loading.postValue(true)
                },
                { error.postValue(true);  }
            )
    }
}
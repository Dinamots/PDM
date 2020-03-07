package fr.m1miage.tmdb.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    val searchSting = MutableLiveData<String>()
}

package fr.m1miage.tmdb.ui.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.m1miage.tmdb.api.model.Person

class PersonViewModel : ViewModel() {
    val person: MutableLiveData<Person> = MutableLiveData<Person>()
}

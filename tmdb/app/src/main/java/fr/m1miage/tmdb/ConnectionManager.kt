package fr.m1miage.tmdb

import androidx.lifecycle.MutableLiveData

public class ConnectionManager {
    companion object {
        val isConnected: MutableLiveData<Boolean> = MutableLiveData()
    }
}
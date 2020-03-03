package fr.m1miage.tmdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.api.model.Search
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        setContentView(R.layout.activity_main)
    }
}

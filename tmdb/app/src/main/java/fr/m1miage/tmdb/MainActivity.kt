package fr.m1miage.tmdb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import fr.m1miage.tmdb.api.RetrofitManager
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        setContentView(R.layout.activity_main)

        top_rated_movies.layoutManager = LinearLayoutManager(this)
        initMovieLists()
    }

    private fun initMovieLists() {
        RetrofitManager.tmdbAPI.getNowPlaying()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({})
    }

    private fun initAdapter(): MovieAdapter {
        val adapter = MovieAdapter(listOf()) { feature ->
            run {
                // val intent = Intent(this, AgenceDetailActivity::class.java)
                // intent.putExtra("feature", feature)
                // startActivity(intent)
            }
        }
        top_rated_movies.adapter = adapter
        upcoming_movies.adapter = adapter
        now_playing_movies.adapter = adapter
        popular_movies.adapter = adapter
        return adapter
    }
}

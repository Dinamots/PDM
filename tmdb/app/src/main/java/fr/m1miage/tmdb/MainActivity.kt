package fr.m1miage.tmdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.api.model.Search
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val adapterMap: HashMap<Int, MovieAdapter> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        setContentView(R.layout.activity_main)
        initLayoutManagers()
        initAdapters()
        initMovieLists()
    }

    private fun initLayoutManagers() {
        top_rated_movies.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        upcoming_movies.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        now_playing_movies.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        popular_movies.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun initAdapters() {
        initAdapter(top_rated_movies, getAdapter(getString(R.string.top_rated_movies)))
        initAdapter(upcoming_movies, getAdapter(getString(R.string.upcoming_movies)))
        initAdapter(now_playing_movies, getAdapter(getString(R.string.now_playing_movies)))
        initAdapter(popular_movies, getAdapter(getString(R.string.popular_movies)))
    }

    private fun initAdapter(recyclerView: RecyclerView?, adapter: MovieAdapter) {
        recyclerView?.adapter = adapter
        if (recyclerView != null) adapterMap[recyclerView.id] = adapter

    }

    private fun initMovieLists() {
        initMovieList(RetrofitManager.tmdbAPI.getNowPlaying(), adapterMap[now_playing_movies.id])
        initMovieList(RetrofitManager.tmdbAPI.getPopular(), adapterMap[popular_movies.id])
        initMovieList(RetrofitManager.tmdbAPI.getTopRated(), adapterMap[top_rated_movies.id])
        initMovieList(RetrofitManager.tmdbAPI.getUpcoming(), adapterMap[upcoming_movies.id])

    }

    private fun initMovieList(
        movieObservable: Observable<Search<MovieResponse>>,
        movieAdapter: MovieAdapter?
    ) {
        val res = movieObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res ->
                    movieAdapter?.movies = res.results
                    movieAdapter?.notifyDataSetChanged()
                },
                { err -> err.printStackTrace() }
            )
    }

    private fun getAdapter(headerString: String): MovieAdapter {
        return MovieAdapter(listOf(), headerString) { feature ->
            run {
                // val intent = Intent(this, AgenceDetailActivity::class.java)
                // intent.putExtra("feature", feature)
                // startActivity(intent)
            }
        }
    }
}

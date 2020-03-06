package fr.m1miage.tmdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.m1miage.tmdb.MovieAdapter
import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.api.model.Search
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private val adapterMap: HashMap<Int, MovieAdapter> = HashMap()
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        initLayoutManagers()
        initAdapters()
        initMovieLists()
        return root
    }


    private fun initLayoutManagers() {
        root.findViewById<RecyclerView>(R.id.top_rated_movies)?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        root.findViewById<RecyclerView>(R.id.upcoming_movies)?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        root.findViewById<RecyclerView>(R.id.now_playing_movies)?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        root.findViewById<RecyclerView>(R.id.popular_movies)?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun initAdapters() {
        initAdapter(
            root.findViewById(R.id.top_rated_movies),
            getAdapter(getString(R.string.top_rated_movies))
        )
        initAdapter(
            root.findViewById(R.id.upcoming_movies),
            getAdapter(getString(R.string.upcoming_movies))
        )
        initAdapter(
            root.findViewById(R.id.now_playing_movies),
            getAdapter(getString(R.string.now_playing_movies))
        )
        initAdapter(
            root.findViewById(R.id.popular_movies),
            getAdapter(getString(R.string.popular_movies))
        )
    }

    private fun initAdapter(recyclerView: RecyclerView?, adapter: MovieAdapter) {
        recyclerView?.adapter = adapter
        if (recyclerView != null) adapterMap[recyclerView.id] = adapter

    }

    private fun initMovieLists() {
        initMovieList(
            RetrofitManager.tmdbAPI.getNowPlaying(),
            adapterMap[root.findViewById<RecyclerView>(R.id.now_playing_movies).id]
        )
        initMovieList(
            RetrofitManager.tmdbAPI.getPopular(),
            adapterMap[root.findViewById<RecyclerView>(R.id.popular_movies).id]
        )
        initMovieList(
            RetrofitManager.tmdbAPI.getTopRated(),
            adapterMap[root.findViewById<RecyclerView>(R.id.top_rated_movies).id]
        )
        initMovieList(
            RetrofitManager.tmdbAPI.getUpcoming(),
            adapterMap[root.findViewById<RecyclerView>(R.id.upcoming_movies).id]
        )
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

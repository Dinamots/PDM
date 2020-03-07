package fr.m1miage.tmdb.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.m1miage.tmdb.adapter.MovieAdapter
import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.utils.extension.addOrRemoveMovie
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    private val adapterMap: HashMap<Int, MovieAdapter> = HashMap()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)
        initLayoutManagers()
        initAdapters()
        initMovieLists()
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE);
        preferences?.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            println(key)
        }

        return root
    }


    private fun initLayoutManagers() {
        root.top_rated_movies?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        root.upcoming_movies?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        root.now_playing_movies?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        root.popular_movies?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun initAdapters() {
        initAdapter(
            root.top_rated_movies,
            getAdapter(getString(R.string.top_rated_movies))
        )
        initAdapter(
            root.upcoming_movies,
            getAdapter(getString(R.string.upcoming_movies))
        )
        initAdapter(
            root.now_playing_movies,
            getAdapter(getString(R.string.now_playing_movies))
        )
        initAdapter(
            root.popular_movies,
            getAdapter(getString(R.string.popular_movies))
        )
    }

    private fun initAdapter(recyclerView: RecyclerView?, adapter: MovieAdapter) {
        if (recyclerView?.adapter == null) {
            recyclerView?.adapter = adapter
        }
        if (recyclerView != null) adapterMap[recyclerView.id] = adapter

    }

    private fun initMovieLists() {
        initMovieList(
            homeViewModel.getNowPlayingMovies(),
            adapterMap[root.now_playing_movies.id]
        )

        initMovieList(
            homeViewModel.getPopularMovies(),
            adapterMap[root.popular_movies.id]
        )

        initMovieList(
            homeViewModel.getTopRatedMovies(),
            adapterMap[root.top_rated_movies.id]
        )

        initMovieList(
            homeViewModel.getUpcomingMovies(),
            adapterMap[root.upcoming_movies.id]
        )
    }

    private fun initMovieList(
        movies: LiveData<List<MovieResponse>>,
        movieAdapter: MovieAdapter?
    ) {
        movies.observe(viewLifecycleOwner, Observer {
            movieAdapter?.movies = it as MutableList<MovieResponse>
        })
    }

    private fun getAdapter(headerString: String): MovieAdapter {
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE);
        return MovieAdapter(
            mutableListOf(), headerString, preferences,
            { movieResponse -> println(movieResponse.title) },
            { intent -> startActivity(intent) })
        { movieResponse, _ ->
            preferences?.addOrRemoveMovie(movieResponse)
        }

    }


}

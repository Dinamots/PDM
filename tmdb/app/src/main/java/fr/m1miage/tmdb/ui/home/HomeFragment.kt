package fr.m1miage.tmdb.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.m1miage.tmdb.adapter.MovieAdapter
import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.ui.movie.MovieDetailViewModel
import fr.m1miage.tmdb.utils.MOVIE_MAP_PLAYING_KEY
import fr.m1miage.tmdb.utils.MOVIE_MAP_POPULAR_KEY
import fr.m1miage.tmdb.utils.MOVIE_MAP_TOP_KEY
import fr.m1miage.tmdb.utils.MOVIE_MAP_UPCOMING_KEY
import fr.m1miage.tmdb.utils.extension.addMovieList
import fr.m1miage.tmdb.utils.extension.addOrRemoveMovie
import fr.m1miage.tmdb.utils.extension.getMovieMap
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    private val adapterMap: HashMap<Int, MovieAdapter> = HashMap()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var preferences: SharedPreferences
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)
        preferences = activity?.getPreferences(Context.MODE_PRIVATE)!!
        initAdapters()
        initMovieLists()
        initLayoutManagers()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            homeViewModel.nowPlayingMovies,
            homeViewModel.onErrorNowPlaying,
            adapterMap[root.now_playing_movies.id],
            MOVIE_MAP_PLAYING_KEY
        )

        initMovieList(
            homeViewModel.popularMovies,
            homeViewModel.onErrorPopular,
            adapterMap[root.popular_movies.id],
            MOVIE_MAP_POPULAR_KEY
        )

        initMovieList(
            homeViewModel.topRatedMovies,
            homeViewModel.onErrorTopRated,
            adapterMap[root.top_rated_movies.id],
            MOVIE_MAP_TOP_KEY
        )

        initMovieList(
            homeViewModel.upcomingMovies,
            homeViewModel.onErrorUpcoming,
            adapterMap[root.upcoming_movies.id],
            MOVIE_MAP_UPCOMING_KEY
        )
    }

    private fun initMovieList(
        movies: LiveData<List<MovieResponse>>,
        error: MutableLiveData<Boolean>,
        movieAdapter: MovieAdapter?,
        key: String
    ) {
        movies.observe(viewLifecycleOwner, Observer {
            preferences.addMovieList(it,key)
            movieAdapter?.movies = it.toMutableList()
            movieAdapter?.notifyDataSetChanged()
        })

        error.observe(viewLifecycleOwner, Observer {
            if(it) {
                movieAdapter?.movies = preferences.getMovieMap()[key]?.toMutableList()!!
                movieAdapter?.notifyDataSetChanged()
            }
        })

    }

    private fun getAdapter(headerString: String): MovieAdapter {
        return MovieAdapter(
            mutableListOf(),
            headerString,
            preferences,
            {
                val navController = findNavController(activity!!, R.id.nav_host_fragment)
                val movieDetailViewModel: MovieDetailViewModel by activityViewModels()
                navController.navigate(R.id.nav_movie_detail)
                movieDetailViewModel.movieId.postValue(it.id)
            }
        ) { movieResponse, _ ->
            preferences.addOrRemoveMovie(movieResponse)
        }

    }


}

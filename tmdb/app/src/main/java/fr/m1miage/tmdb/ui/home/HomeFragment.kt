package fr.m1miage.tmdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.m1miage.tmdb.MovieAdapter
import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.MovieResponse

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
        if (recyclerView?.adapter == null) {
            recyclerView?.adapter = adapter
        }
        if (recyclerView != null) adapterMap[recyclerView.id] = adapter

    }

    private fun initMovieLists() {
        initMovieList(
            homeViewModel.getNowPlayingMovies(),
            adapterMap[root.findViewById<RecyclerView>(R.id.now_playing_movies).id]
        )

        initMovieList(
            homeViewModel.getPopularMovies(),
            adapterMap[root.findViewById<RecyclerView>(R.id.popular_movies).id]
        )

        initMovieList(
            homeViewModel.getTopRatedMovies(),
            adapterMap[root.findViewById<RecyclerView>(R.id.top_rated_movies).id]
        )

        initMovieList(
            homeViewModel.getUpcomingMovies(),
            adapterMap[root.findViewById<RecyclerView>(R.id.upcoming_movies).id]
        )
    }

    private fun initMovieList(
        upcomingMovies: LiveData<List<MovieResponse>>,
        movieAdapter: MovieAdapter?
    ) {
        upcomingMovies.observe(viewLifecycleOwner, Observer {
            movieAdapter?.movies = it
        })
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

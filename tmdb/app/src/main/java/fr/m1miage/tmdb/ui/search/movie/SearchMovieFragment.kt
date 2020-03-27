package fr.m1miage.tmdb.ui.search.movie

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.adapter.MovieAdapter
import fr.m1miage.tmdb.adapter.PaginationListener
import fr.m1miage.tmdb.ui.movie.MovieDetailViewModel
import fr.m1miage.tmdb.ui.search.SearchViewModel
import fr.m1miage.tmdb.utils.extension.addOrRemoveMovie
import fr.m1miage.tmdb.utils.extension.getFavorites
import kotlinx.android.synthetic.main.search_movie_fragment.*

class SearchMovieFragment : Fragment() {
    val searchViewModel: SearchViewModel by activityViewModels()
    var totalPages = 1
    var currentPage = 1
    var searchString = ""
    var newSearch = true
    companion object {
        fun newInstance() = SearchMovieFragment()
    }

    private lateinit var viewModel: SearchMovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_movie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = getAdapter()
        val layout = GridLayoutManager(context, 3)
        search_movie_recycler_view.adapter = adapter
        search_movie_recycler_view.layoutManager = layout
        searchViewModel.searchSting.observe(viewLifecycleOwner, Observer {
            newSearch = true
            searchString = it
            currentPage = 1
            searchViewModel.fetchMovies(searchString, currentPage)
        })

        search_movie_recycler_view.addOnScrollListener(object : PaginationListener(layout) {
            override fun loadMoreItems() {
                currentPage++
                searchViewModel.fetchMovies(searchString, currentPage)
            }

            override fun isLastPage(): Boolean {
                return currentPage == totalPages
            }

            override fun isLoading(): Boolean {
                return false
            }

        })

        searchViewModel.movies.observe(viewLifecycleOwner, Observer {
            if(newSearch) {
                adapter.movies = it.toMutableList()
                newSearch = false
            } else {
                adapter.movies.addAll(it)
            }
            adapter.notifyDataSetChanged()
        })

        searchViewModel.totalPages.observe(viewLifecycleOwner, Observer {
            totalPages = it
        })    }

    private fun getAdapter(): MovieAdapter {
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE);
        return MovieAdapter(
            mutableListOf(),
            null,
            preferences,
            {
                val navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                val movieDetailViewModel: MovieDetailViewModel by activityViewModels()
                navController.navigate(R.id.nav_movie_detail)
                movieDetailViewModel.movieId.value = it.id
            }
        )
        { movieResponse, _ ->
            preferences?.addOrRemoveMovie(movieResponse)
            println(preferences?.getFavorites()?.movies)
        }
    }

}

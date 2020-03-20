package fr.m1miage.tmdb.ui.search

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.adapter.MovieAdapter
import fr.m1miage.tmdb.adapter.PaginationListener
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.listeners.button.OnClickListenerMovie
import fr.m1miage.tmdb.ui.movie.MovieDetailViewModel
import fr.m1miage.tmdb.utils.extension.addOrRemoveMovie
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment() {
    val searchViewModel: SearchViewModel by activityViewModels()
    var totalPages = 1
    var currentPage = 1
    var searchString = ""

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = getAdapter()
        val layout = GridLayoutManager(context, 3)
        search_recycler_view.adapter = adapter
        search_recycler_view.layoutManager = layout
        searchViewModel.searchSting.observe(viewLifecycleOwner, Observer {
            searchString = it
            currentPage = 1
            RetrofitManager.tmdbAPI.searchMovies(it, currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { res ->
                        adapter.movies = res.results.toMutableList()
                        adapter.notifyDataSetChanged()

                        totalPages = res.total_pages!!
                    },
                    { error -> error.printStackTrace() }
                )
        })
        search_recycler_view.addOnScrollListener(object : PaginationListener(layout) {
            override fun loadMoreItems() {
                currentPage++
                val disposable = RetrofitManager.tmdbAPI.searchMovies(searchString, currentPage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { res ->
                            adapter.movies.addAll(res.results.toMutableList())
                            adapter.notifyDataSetChanged()
                        },
                        { error -> error.printStackTrace() }
                    )

            }

            override fun isLastPage(): Boolean {
                return currentPage == totalPages
            }

            override fun isLoading(): Boolean {
                return false
            }

        })
    }

    private fun getAdapter(): MovieAdapter {
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE);
        return MovieAdapter(
            mutableListOf(),
            null,
            preferences,
            {
                val navController = findNavController(activity!!, R.id.nav_host_fragment)
                val movieDetailViewModel: MovieDetailViewModel by activityViewModels()
                navController.navigate(R.id.nav_movie_detail)
                movieDetailViewModel.movieId.value = it.id
            }
        )
        { movieResponse, _ ->
            preferences?.addOrRemoveMovie(movieResponse)
        }
    }
}

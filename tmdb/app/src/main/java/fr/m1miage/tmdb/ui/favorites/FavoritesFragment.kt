package fr.m1miage.tmdb.ui.favorites

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.m1miage.tmdb.ConnectionManager
import fr.m1miage.tmdb.adapter.MovieAdapter
import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.ui.movie.MovieDetailViewModel
import fr.m1miage.tmdb.utils.MAX_SPAN_COUNT
import fr.m1miage.tmdb.utils.extension.addOrRemoveMovie
import fr.m1miage.tmdb.utils.extension.getFavorites
import fr.m1miage.tmdb.utils.snack
import kotlinx.android.synthetic.main.fragment_favorites.view.*

class FavoritesFragment : Fragment() {
    private val slideshowViewModel: FavoritesViewModel by activityViewModels()
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_favorites, container, false)
        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        root.favorites_recycler_view?.layoutManager = GridLayoutManager(context, MAX_SPAN_COUNT)

        root.favorites_recycler_view?.adapter =
            MovieAdapter(
                sharedPreferences!!.getFavorites().movies,
                null,
                sharedPreferences,
                {
                    if (ConnectionManager.isConnected.value == true) {
                        val navController = findNavController(activity!!, R.id.nav_host_fragment)
                        val movieDetailViewModel: MovieDetailViewModel by activityViewModels()
                        navController.navigate(R.id.nav_movie_detail)
                        movieDetailViewModel.movieId.value = it.id
                    } else {
                        snack(view!!, getString(R.string.connection_needed))
                    }

                }
            ) { movieResponse, adapter ->
                onFavoriteButtonClick(sharedPreferences, movieResponse, adapter)
            }
        return root
    }

    private fun onItemClick(movieResponse: MovieResponse) {
        println(movieResponse.title)
    }

    private fun onFavoriteButtonClick(
        sharedPreferences: SharedPreferences,
        movieResponse: MovieResponse,
        adapter: MovieAdapter
    ) {
        sharedPreferences.addOrRemoveMovie(movieResponse)
        adapter.movies.remove(movieResponse)
        adapter.notifyDataSetChanged()
    }
}

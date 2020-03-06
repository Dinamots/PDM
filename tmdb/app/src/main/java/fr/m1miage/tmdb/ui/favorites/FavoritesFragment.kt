package fr.m1miage.tmdb.ui.favorites

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.m1miage.tmdb.FavoriteMovieAdapter
import fr.m1miage.tmdb.MovieAdapter
import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.utils.extension.addOrRemoveMovie
import fr.m1miage.tmdb.utils.extension.getFavorites

class FavoritesFragment : Fragment() {
    private val slideshowViewModel: FavoritesViewModel by activityViewModels()
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_favorites, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.favorites_recycler_view)
        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        recyclerView?.layoutManager = GridLayoutManager(context, 3)

        recyclerView?.adapter =
            MovieAdapter(
                sharedPreferences!!.getFavorites().movies,
                null,
                sharedPreferences,
                { movieResponse -> onItemClick(movieResponse) },
                { movieResponse, adapter ->
                    onFavoriteButtonClick(sharedPreferences, movieResponse, adapter)
                }
            )
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
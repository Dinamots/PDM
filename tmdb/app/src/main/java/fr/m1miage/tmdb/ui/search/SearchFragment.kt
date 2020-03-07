package fr.m1miage.tmdb.ui.search

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.adapter.MovieAdapter
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.utils.extension.addOrRemoveMovie
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment() {
    val searchViewModel: SearchViewModel by activityViewModels()

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
        search_recycler_view.adapter = adapter
        search_recycler_view.layoutManager = GridLayoutManager(context, 3)
        searchViewModel.searchSting.observe(viewLifecycleOwner, Observer {
            RetrofitManager.tmdbAPI.searchMovies(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                    {res -> adapter.movies = res.results.toMutableList(); adapter.notifyDataSetChanged()},
                    {error -> error.printStackTrace()}
                )
        })
    }

    private fun getAdapter(): MovieAdapter {
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE);
        return MovieAdapter(
            mutableListOf(),
            null,
            preferences,
            { }) { movieResponse, _ ->
            preferences?.addOrRemoveMovie(movieResponse)
        }
    }
}

package fr.m1miage.tmdb.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.api.RetrofitManager
import io.reactivex.android.schedulers.AndroidSchedulers

class MovieDetailFragment : Fragment() {
    val movieDetailViewModel: MovieDetailViewModel by activityViewModels()

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieDetailViewModel.movieId.observe(viewLifecycleOwner, Observer {
           movieDetailViewModel.movie().observe(viewLifecycleOwner, Observer {

           })
        })
    }

}

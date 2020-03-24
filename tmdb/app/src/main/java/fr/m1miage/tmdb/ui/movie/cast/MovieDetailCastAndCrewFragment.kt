package fr.m1miage.tmdb.ui.movie.cast

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.m1miage.tmdb.R

class MovieDetailCastAndCrewFragment : Fragment() {

    companion object {
        fun newInstance() =
            MovieDetailCastAndCrewFragment()
    }

    private lateinit var viewModel: MovieDetailCastAndCrewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_cast_and_crew_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MovieDetailCastAndCrewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

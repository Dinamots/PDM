package fr.m1miage.tmdb.ui.movie.cast

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.adapter.PersonAdapter
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Credits
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.api.model.Person
import fr.m1miage.tmdb.ui.movie.MovieDetailViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.movie_detail_cast_and_crew_fragment.*

class MovieDetailCastAndCrewFragment : Fragment() {
    val crewAdapter: PersonAdapter = getAdapter()


    val castAdapter: PersonAdapter = getAdapter()

    companion object {
        fun newInstance() =
            MovieDetailCastAndCrewFragment()
    }

    val movieDetailViewModel: MovieDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_cast_and_crew_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieDetailViewModel.movie.observe(viewLifecycleOwner, Observer {
            RetrofitManager.tmdbAPI.getCredits(it.id.toLong())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { credits: Credits -> initView(credits) },
                    { err -> println(err) }
                )
        })
    }

    private fun getAdapter(): PersonAdapter {
        return PersonAdapter(listOf()) {
            println(it)
        }
    }


    private fun initView(credits: Credits) {

        initRecyclerView(credits.cast, castAdapter, cast_recycler_view)
        initRecyclerView(credits.crew, crewAdapter, crew_recycler_view)

    }

    private fun initRecyclerView(
        persons: List<Person>,
        adapter: PersonAdapter,
        recyclerView: RecyclerView
    ) {
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        println("height = " + recyclerView.height)
        recyclerView.adapter = adapter
        adapter.persons = persons
        adapter.notifyDataSetChanged()


    }


}

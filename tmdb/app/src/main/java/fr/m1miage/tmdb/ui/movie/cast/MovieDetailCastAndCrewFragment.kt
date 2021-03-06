package fr.m1miage.tmdb.ui.movie.cast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.m1miage.tmdb.ConnectionManager

import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.adapter.PersonAdapter
import fr.m1miage.tmdb.api.model.Credits
import fr.m1miage.tmdb.api.model.Person
import fr.m1miage.tmdb.ui.movie.MovieDetailViewModel
import fr.m1miage.tmdb.ui.person.PersonViewModel
import fr.m1miage.tmdb.utils.snack
import kotlinx.android.synthetic.main.movie_detail_cast_and_crew_fragment.*

class MovieDetailCastAndCrewFragment : Fragment() {
    val crewAdapter: PersonAdapter = getAdapter()
    val movieDetailViewModel: MovieDetailViewModel by activityViewModels()
    val personViewModel: PersonViewModel by activityViewModels()
    val movieDetailCastAndCrewViewModel: MovieDetailCastAndCrewViewModel by activityViewModels()
    val castAdapter: PersonAdapter = getAdapter()

    companion object {
        fun newInstance() =
            MovieDetailCastAndCrewFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_cast_and_crew_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieDetailViewModel.movie.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                movieDetailCastAndCrewViewModel.fetchCredits(it.id)
            }
        })

        movieDetailCastAndCrewViewModel.credits.observe(viewLifecycleOwner, Observer {
            initView(it)
        })
    }

    private fun getAdapter(): PersonAdapter {
        return PersonAdapter(mutableListOf()) { onClickPerson(it) }
    }

    private fun onClickPerson(it: Person) {
        if (ConnectionManager.isConnected.value == true) {
            val navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            navController.navigate(R.id.nav_person)
            personViewModel.personId.postValue(it.id)
        } else {
            snack(view!!, getString(R.string.connection_needed))
        }
    }


    private fun initView(credits: Credits<Person>) {
        initRecyclerView(credits.cast, castAdapter, cast_recycler_view)
        initRecyclerView(credits.crew, crewAdapter, crew_recycler_view)

    }

    private fun initRecyclerView(
        persons: List<Person>,
        adapter: PersonAdapter,
        recyclerView: RecyclerView
    ) {
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = adapter
        adapter.persons = persons.toMutableList()
        adapter.notifyDataSetChanged()
    }


}

package fr.m1miage.tmdb.ui.search.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import fr.m1miage.tmdb.ConnectionManager

import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.adapter.PaginationListener
import fr.m1miage.tmdb.adapter.PersonAdapter
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.api.model.Person
import fr.m1miage.tmdb.api.model.PersonResponse
import fr.m1miage.tmdb.ui.person.PersonViewModel
import fr.m1miage.tmdb.ui.search.SearchViewModel
import fr.m1miage.tmdb.utils.MAX_SPAN_COUNT
import fr.m1miage.tmdb.utils.extension.toPersons
import fr.m1miage.tmdb.utils.snack
import kotlinx.android.synthetic.main.search_person_fragment.*
import kotlinx.android.synthetic.main.search_person_fragment.no_results

class SearchPersonFragment : Fragment() {
    val searchViewModel: SearchViewModel by activityViewModels()
    val searchPersonViewModel: SearchPersonViewModel by activityViewModels()
    val personViewModel: PersonViewModel by activityViewModels()
    var totalPages = 1
    var currentPage = 1
    var searchString = ""
    var newSearch = true

    companion object {
        fun newInstance() = SearchPersonFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_person_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = getAdapter()
        val layout = GridLayoutManager(context, MAX_SPAN_COUNT)
        search_person_recycler_view.adapter = adapter
        search_person_recycler_view.layoutManager = layout

        searchViewModel.searchSting.observe(viewLifecycleOwner, Observer { onSearch(it) })

        searchPersonViewModel.totalPages.observe(viewLifecycleOwner, Observer {
            totalPages = it
        })

        searchPersonViewModel.persons.observe(viewLifecycleOwner, Observer {
            onPersonsSuccess(adapter, it)
        })

        search_person_recycler_view.addOnScrollListener(object : PaginationListener(layout) {
            override fun loadMoreItems() {
                currentPage++
                searchPersonViewModel.fetchPersons(searchString, currentPage)
            }

            override fun isLastPage(): Boolean {
                return currentPage == totalPages
            }

            override fun isLoading(): Boolean {
                return false
            }

        })
    }

    private fun onPersonsSuccess(
        adapter: PersonAdapter,
        it: List<PersonResponse>
    ) {
        if (newSearch) {
            adapter.persons = it.toPersons().toMutableList()
            onNoResults(it)
            newSearch = false
        } else {
            adapter.persons.addAll(it.toPersons().toMutableList())
        }
        adapter.notifyDataSetChanged()
    }

    private fun onSearch(it: String) {
        newSearch = true
        searchString = it
        currentPage = 1
        searchPersonViewModel.fetchPersons(searchString, currentPage)
    }

    private fun getAdapter(): PersonAdapter {
        return PersonAdapter(
            mutableListOf()
        ) { onClickPerson(it) }
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

    private fun onNoResults(persons: List<PersonResponse>) {
        if (persons.isEmpty()) {
            no_results.visibility = View.VISIBLE
            search_person_recycler_view.visibility = View.GONE
        } else {
            no_results.visibility = View.GONE
            search_person_recycler_view.visibility = View.VISIBLE
        }
    }

}

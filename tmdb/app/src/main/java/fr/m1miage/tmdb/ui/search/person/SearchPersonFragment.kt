package fr.m1miage.tmdb.ui.search.person

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.m1miage.tmdb.R

class SearchPersonFragment : Fragment() {

    companion object {
        fun newInstance() = SearchPersonFragment()
    }

    private lateinit var viewModel: SearchPersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_person_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchPersonViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

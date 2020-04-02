package fr.m1miage.tmdb.ui.internet

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.m1miage.tmdb.R

class NoInternetFragment : Fragment() {

    companion object {
        fun newInstance() = NoInternetFragment()
    }

    private lateinit var viewModel: NoInternetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.no_internet_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NoInternetViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

package fr.m1miage.tmdb.ui.search

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.adapter.BasicViewPagerAdapter
import fr.m1miage.tmdb.adapter.MovieAdapter
import fr.m1miage.tmdb.adapter.PaginationListener
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.ui.movie.MovieDetailViewModel
import fr.m1miage.tmdb.ui.movie.cast.MovieDetailCastAndCrewFragment
import fr.m1miage.tmdb.ui.movie.infos.MovieDetailInfosFragment
import fr.m1miage.tmdb.ui.search.movie.SearchMovieFragment
import fr.m1miage.tmdb.ui.search.person.SearchPersonFragment
import fr.m1miage.tmdb.utils.extension.addOrRemoveMovie
import fr.m1miage.tmdb.utils.extension.getFavorites
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.android.synthetic.main.search_fragment.view_pager

class SearchFragment : Fragment() {
    val searchViewModel: SearchViewModel by activityViewModels()
    lateinit var pagerAdapter: BasicViewPagerAdapter

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
        pagerAdapter = BasicViewPagerAdapter(
            listOf(
                SearchMovieFragment(),
                SearchPersonFragment()
            ), childFragmentManager, 0
        )
        initTabs()
    }


    private fun initTabs() {
        search_tab_layout.setupWithViewPager(view_pager)
        view_pager.adapter = pagerAdapter
        search_tab_layout.getTabAt(0)?.setIcon(R.drawable.ic_movie_black_24dp)
        search_tab_layout.getTabAt(0)?.text = "Movie"
        search_tab_layout.getTabAt(1)?.setIcon(R.drawable.ic_videocam_black_24dp)
        search_tab_layout.getTabAt(1)?.text = "Cast & Crew"
    }
}

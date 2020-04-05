package fr.m1miage.tmdb.ui.person

import android.content.Context
import android.os.Bundle
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.adapter.MovieAdapter
import fr.m1miage.tmdb.api.model.Credits
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.api.model.PersonDetail
import fr.m1miage.tmdb.ui.movie.MovieDetailViewModel
import fr.m1miage.tmdb.utils.IMDB_PERSON_PATH
import fr.m1miage.tmdb.utils.extension.getImgLink
import fr.m1miage.tmdb.utils.extension.getMovieResponseList
import fr.m1miage.tmdb.utils.formatDate
import kotlinx.android.synthetic.main.person_fragment.*


class PersonFragment : Fragment() {
    lateinit var movieAdapter: MovieAdapter
    val personViewModel: PersonViewModel by activityViewModels()

    companion object {
        fun newInstance() = PersonFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.person_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieAdapter = MovieAdapter(
            mutableListOf(),
            null,
            activity?.getPreferences(Context.MODE_PRIVATE),
            {
                val navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                val movieDetailViewModel: MovieDetailViewModel by activityViewModels()
                navController.navigate(R.id.nav_movie_detail)
                movieDetailViewModel.movieId.value = it.id
            })
        { _, _ -> }

        personViewModel.personId.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                personViewModel.fecthPersonDetail(it)
                personViewModel.fetchFilmography(it)
            }
        })

        personViewModel.personDetail.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                initView(it)
            }
        })

        personViewModel.filmography.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                initMovieList(it)
            }
        })

        personViewModel.onLoadingPersonDetail.observe(viewLifecycleOwner, Observer {
            loading_movie_img.visibility = if (it) View.VISIBLE else View.GONE
            person_header.visibility = if (it) View.GONE else View.VISIBLE
        })

        personViewModel.onLoadingFilmography.observe(viewLifecycleOwner, Observer {
            loading_filmography.visibility = if (it) View.VISIBLE else View.GONE
            person_filmography.visibility = if (it) View.GONE else View.VISIBLE
        })


    }

    private fun initView(
        personDetail: PersonDetail
    ) {
        initPersonImg(personDetail)
        initText(personDetail)

    }

    private fun initText(personDetail: PersonDetail) {

        person_name.text = personDetail.name
        person_birth_death.text = getBirthDeathString(personDetail)
        place_of_birth.text = personDetail.place_of_birth
        imdb_link.movementMethod = LinkMovementMethod.getInstance()
        imdb_link.text = getLink(personDetail)

    }

    private fun getLink(personDetail: PersonDetail): Spanned {
        return HtmlCompat.fromHtml(
            getHtmlLink(personDetail),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    private fun getHtmlLink(personDetail: PersonDetail) =
        "<a href=\"${IMDB_PERSON_PATH + personDetail.imdb_id}\">${getString(R.string.tmdb_link)}</a>"

    private fun getBirthDeathString(personDetail: PersonDetail): String {
        if (personDetail.birthday != null) {
            return "${formatDate(personDetail.birthday)} - ${if (personDetail.deathday != null)
                formatDate(personDetail.deathday) else getString(R.string.alive)}"
        }
        return ""

    }


    private fun initPersonImg(personDetail: PersonDetail) {
        Picasso.get()
            .load(personDetail.getImgLink())
            .fit()
            .into(person_img)
    }

    private fun initMovieList(credits: Credits<MovieResponse>) {
        val movieResponseList: List<MovieResponse> = credits.getMovieResponseList()
        person_filmography.layoutManager = GridLayoutManager(context, 3)
        person_filmography.adapter = movieAdapter
        movieAdapter.movies = movieResponseList.toMutableList()
        movieAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        person_header.visibility = View.GONE
        super.onDestroyView()
    }

    override fun onDestroy() {
        personViewModel.destroy()
        super.onDestroy()

    }

}

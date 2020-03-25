package fr.m1miage.tmdb.ui.person

import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso

import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.adapter.MovieAdapter
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Credits
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.api.model.PersonDetail
import fr.m1miage.tmdb.ui.movie.MovieDetailViewModel
import fr.m1miage.tmdb.utils.IMDB_MOVIE_PATH
import fr.m1miage.tmdb.utils.IMDB_PERSON_PATH
import fr.m1miage.tmdb.utils.extension.getImgLink
import fr.m1miage.tmdb.utils.extension.getMovieResponseList
import fr.m1miage.tmdb.utils.formatDate
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.person_fragment.*

class PersonFragment : Fragment() {
    lateinit var movieAdapter: MovieAdapter

    companion object {
        fun newInstance() = PersonFragment()
    }

    val personViewModel: PersonViewModel by activityViewModels()

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
        personViewModel.person.observe(viewLifecycleOwner, Observer {
            println(it.id)
            RetrofitManager.tmdbAPI.getPerson(it.id.toLong())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { personDetail -> getCredits(personDetail) },
                    { err -> println(err) }
                )

        })

    }

    private fun getCredits(personDetail: PersonDetail) {
        val disposable = RetrofitManager.tmdbAPI.getFilmography(personDetail.id.toLong())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { credits -> initView(credits, personDetail) },
                { err -> println(err) }
            )
    }

    private fun getPersonDetail(credits: Credits<MovieResponse>?) {
    }

    private fun initView(
        credits: Credits<MovieResponse>,
        person: PersonDetail
    ) {
        initMovieList(credits)
        initPersonImg(person)
        initText(person)

    }

    private fun initText(person: PersonDetail) {
        val birthDeathString =
            "${formatDate(person.birthday)} - ${if (person.deathday != null) formatDate(person.deathday) else "alive"}"
        val imdbLink = IMDB_PERSON_PATH + person.imdb_id

        person_name.text = person.name
        person_birth_death.text = birthDeathString
        place_of_birth.text = person.place_of_birth
        imdb_link.text = imdbLink
        imdb_link.movementMethod = LinkMovementMethod.getInstance()


        // biography.text = person.biography
    }

    private fun initPersonImg(person: PersonDetail) {
        Picasso.get()
            .load(person.getImgLink())
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

}

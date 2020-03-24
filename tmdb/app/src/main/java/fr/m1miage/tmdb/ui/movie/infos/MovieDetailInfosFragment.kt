package fr.m1miage.tmdb.ui.movie.infos

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso

import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.ui.movie.MovieDetailViewModel
import fr.m1miage.tmdb.utils.IMDB_PATH
import fr.m1miage.tmdb.utils.TMDB_IMAGES_PATH
import fr.m1miage.tmdb.utils.extension.addOrRemoveMovie
import fr.m1miage.tmdb.utils.extension.isFavoriteMovie
import fr.m1miage.tmdb.utils.extension.toMovieReponse
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import kotlinx.android.synthetic.main.movie_detail_infos_fragment.*
import java.text.SimpleDateFormat

class MovieDetailInfosFragment : Fragment() {

    companion object {
        fun newInstance() = MovieDetailInfosFragment()
    }

    val movieDetailViewModel: MovieDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_infos_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieDetailViewModel.movie.observe(viewLifecycleOwner, Observer {
            initView(it)
        })
        // TODO: Use the ViewModel
    }

    private fun initView(movie: Movie) {
        initFavorite(movie)
        initShareButton(movie)
        initText(movie)
        getMovieImg(movie)
    }

    private fun getMovieImg(movie: Movie) {
        Picasso.get()
            .load(TMDB_IMAGES_PATH + if (movie.poster_path !== "") movie.poster_path else movie.backdrop_path)
            .fit()
            .into(movie_img)

    }

    private fun initShareButton(movie: Movie) {
        movie_share_button.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "${movie.title} \n" +
                            " ${movie.homepage} \n" +
                            " ${IMDB_PATH + movie.imdb_id} \n" +
                            " ${movie.vote_average / 2} / 5 "
                )
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

    }

    private fun initFavorite(
        movie: Movie
    ) {
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE)

        if (preferences!!.isFavoriteMovie(movie.id)) movie_button_favorite.toggle()
        movie_button_favorite.setOnClickListener {
            preferences.addOrRemoveMovie(movie.toMovieReponse())
        }
    }

    private fun initText(movie: Movie) {
        initOverview(movie)
        tagline.text = movie.tagline
    }

    private fun initOverview(movie: Movie) {
        overview.text = movie.overview
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            overview.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
    }




}

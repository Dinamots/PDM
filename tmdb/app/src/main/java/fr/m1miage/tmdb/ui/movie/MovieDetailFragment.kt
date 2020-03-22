package fr.m1miage.tmdb.ui.movie

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.squareup.picasso.Picasso
import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.adapter.GenreAdapter
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.listeners.YoutubeOnInitializedListener
import fr.m1miage.tmdb.utils.GOOFLE_API_KEY
import fr.m1miage.tmdb.utils.IMDB_PATH
import fr.m1miage.tmdb.utils.TMDB_IMAGES_PATH
import fr.m1miage.tmdb.utils.extension.addOrRemoveMovie
import fr.m1miage.tmdb.utils.extension.isFavoriteMovie
import fr.m1miage.tmdb.utils.extension.toMovieReponse
import io.reactivex.android.schedulers.AndroidSchedulers
import jp.wasabeef.picasso.transformations.BlurTransformation
import kotlinx.android.synthetic.main.movie_detail_fragment.*

class MovieDetailFragment : Fragment() {
    val movieDetailViewModel: MovieDetailViewModel by activityViewModels()
    val genreAdapter: GenreAdapter = GenreAdapter(listOf())
    lateinit var youTubePlayerFragment: YouTubePlayerSupportFragment

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_detail_fragment, container, false)
        return view
    }

    private fun initYoutubePlayer(movie: Movie) {
        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance()

        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.youtube_player_fragment, youTubePlayerFragment as Fragment).commit()
        val initializer = YoutubeOnInitializedListener(movie) {
            if (!it) trailer_button.visibility = View.GONE
        }
        youTubePlayerFragment.initialize(GOOFLE_API_KEY, initializer)
        youtube_player_fragment.visibility = View.GONE
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movie_genres.apply { setHasFixedSize(true); adapter = genreAdapter }
        movieDetailViewModel.movieId.observe(viewLifecycleOwner, Observer {
            RetrofitManager.tmdbAPI.getMovie(it.toLong()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { movie -> initView(movie) },
                    { error -> println(error) }
                )
        })
    }

    private fun initView(movie: Movie) {

        getMovieImg(movie)
        getMovieBackground(movie)
        initYoutubePlayer(movie)
        initText(movie)
        initGenres(movie)
        initFavorite(movie)
        initTrailerButton()
        initShareButton(movie)
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

    private fun initTrailerButton() {
        trailer_button.setOnClickListener {
            when (youtube_player_fragment.visibility) {
                View.GONE -> youtube_player_fragment.visibility = View.VISIBLE
                View.VISIBLE -> youtube_player_fragment.visibility = View.GONE
            }
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
        movie_title.text = movie.title
        overview.text = movie.overview
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            overview.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
        tagline.text = movie.tagline
    }

    private fun initGenres(movie: Movie) {
        movie_genres.layoutManager =
            GridLayoutManager(context, if (movie.genres.size >= 3) 3 else movie.genres.size)
        genreAdapter.genres = movie.genres
        genreAdapter.notifyDataSetChanged()
    }

    private fun getMovieBackground(movie: Movie) {
        Picasso.get()
            .load(TMDB_IMAGES_PATH + if (movie.poster_path !== "") movie.poster_path else movie.backdrop_path)
            .fit()
            .into(movie_img)

    }

    private fun getMovieImg(movie: Movie) {

        Picasso.get()
            .load(TMDB_IMAGES_PATH + movie.backdrop_path)
            .transform(BlurTransformation(context, 15, 1))
            .fit()
            .into(movie_backdrop)

    }

}

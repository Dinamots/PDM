package fr.m1miage.tmdb.ui.movie

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.effect.EffectContext
import android.media.effect.EffectFactory
import android.os.Build
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.adapter.GenreAdapter
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Genre
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.utils.TMDB_IMAGES_PATH
import fr.m1miage.tmdb.utils.extension.addOrRemoveMovie
import fr.m1miage.tmdb.utils.extension.isFavoriteMovie
import fr.m1miage.tmdb.utils.extension.toMovieReponse
import io.reactivex.android.schedulers.AndroidSchedulers
import jp.wasabeef.picasso.transformations.BlurTransformation
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import java.lang.Exception


class MovieDetailFragment : Fragment() {
    val movieDetailViewModel: MovieDetailViewModel by activityViewModels()
    val genreAdapter: GenreAdapter = GenreAdapter(listOf())

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initGenres()
        println("LAAA")
        movieDetailViewModel.movieId.observe(viewLifecycleOwner, Observer {
            println("ID = $it")
            RetrofitManager.tmdbAPI.getMovie(it.toLong()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { movie -> initView(movie) },
                    { error -> println(error) }
                )
        })
    }

    private fun initView(movie: Movie) {
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE)

        getMovieImg(movie)
        getMovieBackground(movie)
        movie_title.text = movie.title
        overview.text = movie.overview
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            overview.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
        tagline.text = movie.tagline
        movie_genres.layoutManager =
            GridLayoutManager(context, if (movie.genres.size >= 3) 3 else movie.genres.size)
        genreAdapter.genres = movie.genres
        genreAdapter.notifyDataSetChanged()
        if (preferences!!.isFavoriteMovie(movie.id)) movie_button_favorite.toggle()
        movie_button_favorite.setOnClickListener {
            preferences.addOrRemoveMovie(movie.toMovieReponse())
        }
    }

    private fun initGenres() {
        movie_genres.apply {
            setHasFixedSize(true)
            adapter = genreAdapter
        }
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

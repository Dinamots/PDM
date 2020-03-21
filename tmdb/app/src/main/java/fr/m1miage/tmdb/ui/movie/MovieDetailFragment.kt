package fr.m1miage.tmdb.ui.movie

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.effect.EffectContext
import android.media.effect.EffectFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.utils.TMDB_IMAGES_PATH
import io.reactivex.android.schedulers.AndroidSchedulers
import jp.wasabeef.picasso.transformations.BlurTransformation
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import java.lang.Exception


class MovieDetailFragment : Fragment() {
    val movieDetailViewModel: MovieDetailViewModel by activityViewModels()
    val target = object : Target {
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            println("prepare")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                header_movie_layout.background = placeHolderDrawable
            }
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            e?.printStackTrace()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                header_movie_layout.background = errorDrawable
            }
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                header_movie_layout.background = BitmapDrawable(context?.resources, bitmap)
            }
        }

    }

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
        movie_title.text = movie.title
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

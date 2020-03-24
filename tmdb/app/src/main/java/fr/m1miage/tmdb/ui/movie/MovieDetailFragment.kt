package fr.m1miage.tmdb.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.squareup.picasso.Picasso
import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.adapter.GenreAdapter
import fr.m1miage.tmdb.adapter.MovieViewPagerAdapter
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.api.model.Video
import fr.m1miage.tmdb.listeners.YoutubeOnInitializedListener
import fr.m1miage.tmdb.ui.movie.cast.MovieDetailCastAndCrewFragment
import fr.m1miage.tmdb.ui.movie.infos.MovieDetailInfosFragment
import fr.m1miage.tmdb.utils.GOOFLE_API_KEY
import fr.m1miage.tmdb.utils.TMDB_IMAGES_PATH
import io.reactivex.android.schedulers.AndroidSchedulers
import jp.wasabeef.picasso.transformations.BlurTransformation
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import java.text.SimpleDateFormat
import java.util.function.Consumer


class MovieDetailFragment : Fragment() {
    val movieDetailViewModel: MovieDetailViewModel by activityViewModels()
    val genreAdapter: GenreAdapter = GenreAdapter(listOf())
    lateinit var youTubePlayerFragment: YouTubePlayerSupportFragment
    lateinit var youtubePlayer: YouTubePlayer
    lateinit var videos: List<Video>
    lateinit var pagerAdapter: MovieViewPagerAdapter

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
        youtube_player_fragment.visibility = View.GONE
        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance()

        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.youtube_player_fragment, youTubePlayerFragment as Fragment).commit()
        val initializer = YoutubeOnInitializedListener(movie) { youtubePlayer = it }
        youTubePlayerFragment.initialize(GOOFLE_API_KEY, initializer)

        val disposable = RetrofitManager.tmdbAPI.getVideos(movie.id.toLong())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { search -> videos = search.results },
                { err -> println(err) }
            )

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pagerAdapter = MovieViewPagerAdapter(
            listOf(
                MovieDetailInfosFragment(),
                MovieDetailCastAndCrewFragment()
            ), childFragmentManager, 0
        )
        movie_genres.apply { setHasFixedSize(true); adapter = genreAdapter }
        movie_detail_tab_layout.setupWithViewPager(view_pager)
        view_pager.adapter = pagerAdapter
        movie_detail_tab_layout.getTabAt(0)?.setIcon(R.drawable.ic_movie_black_24dp)
        movie_detail_tab_layout.getTabAt(0)?.text = "Movie"
        movie_detail_tab_layout.getTabAt(1)?.setIcon(R.drawable.ic_videocam_black_24dp)
        movie_detail_tab_layout.getTabAt(1)?.text = "Cast & Crew"
        movieDetailViewModel.movieId.observe(viewLifecycleOwner, Observer {
            RetrofitManager.tmdbAPI.getMovie(it.toLong()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { movie -> movieDetailViewModel.movie.value = movie; initView(movie) },
                    { error -> println(error) }
                )
        })
    }

    private fun initView(movie: Movie) {

        getMovieBackground(movie)
        initYoutubePlayer(movie)
        initText(movie)
        initGenres(movie)
        initTrailerButton()
        movie_rating.rating = (movie.vote_average / 2).toFloat()

    }

    private fun initTrailerButton() {
        trailer_button.setOnClickListener {
            when (youtube_player_fragment.visibility) {
                View.GONE -> {
                    youtube_player_fragment.visibility = View.VISIBLE
                    youtubePlayer.loadVideo(videos[0].key)
                }
                View.VISIBLE -> {
                    youtube_player_fragment.visibility = View.GONE
                    youtubePlayer.pause()
                }
            }
        }
    }

    private fun initText(movie: Movie) {
        movie_title.text = movie.title
        release.text = SimpleDateFormat("dd-MM-yyyy").format(movie.release_date)
    }


    private fun initGenres(movie: Movie) {
        movie_genres.layoutManager =
            GridLayoutManager(context, if (movie.genres.size >= 3) 3 else movie.genres.size)
        genreAdapter.genres = movie.genres
        genreAdapter.notifyDataSetChanged()
    }


    private fun getMovieBackground(movie: Movie) {

        Picasso.get()
            .load(TMDB_IMAGES_PATH + movie.backdrop_path)
            .transform(BlurTransformation(context, 15, 1))
            .fit()
            .into(movie_backdrop)
    }

}

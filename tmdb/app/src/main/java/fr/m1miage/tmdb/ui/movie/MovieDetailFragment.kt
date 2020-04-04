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
import fr.m1miage.tmdb.adapter.BasicViewPagerAdapter
import fr.m1miage.tmdb.api.model.Movie
import fr.m1miage.tmdb.api.model.Video
import fr.m1miage.tmdb.listeners.YoutubeOnInitializedListener
import fr.m1miage.tmdb.ui.movie.cast.MovieDetailCastAndCrewFragment
import fr.m1miage.tmdb.ui.movie.infos.MovieDetailInfosFragment
import fr.m1miage.tmdb.utils.*
import jp.wasabeef.picasso.transformations.BlurTransformation
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import kotlinx.android.synthetic.main.movie_detail_fragment.movie_rating
import java.text.SimpleDateFormat


class MovieDetailFragment : Fragment() {
    val movieDetailViewModel: MovieDetailViewModel by activityViewModels()
    val genreAdapter: GenreAdapter = GenreAdapter(listOf())
    lateinit var youTubePlayerFragment: YouTubePlayerSupportFragment
    lateinit var youtubePlayer: YouTubePlayer
    lateinit var videos: List<Video>
    lateinit var pagerAdapter: BasicViewPagerAdapter
    var error = false

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

    private fun initYoutubePlayer() {
        youtube_player_fragment.visibility = View.GONE
        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance()

        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.youtube_player_fragment, youTubePlayerFragment as Fragment).commit()
        val initializer = YoutubeOnInitializedListener { youtubePlayer = it }
        youTubePlayerFragment.initialize(GOOFLE_API_KEY, initializer)

        movieDetailViewModel.videos.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                trailer_button.visibility = View.GONE
            }
        })

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pagerAdapter = BasicViewPagerAdapter(
            listOf(
                MovieDetailInfosFragment(),
                MovieDetailCastAndCrewFragment()
            ), childFragmentManager, 0
        )
        movie_genres.apply { setHasFixedSize(true); adapter = genreAdapter }
        initTabs()
        initYoutubePlayer()
        movieDetailViewModel.onLoadingMovie
        movieDetailViewModel.movieId.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                movieDetailViewModel.fetchAll(it)
            }
        })


        movieDetailViewModel.movie.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                initView(it)
            }
        })

        movieDetailViewModel.videos.observe(viewLifecycleOwner, Observer {
            videos = it
            if (it.isNotEmpty()) {
                trailer_button.visibility = View.VISIBLE
            }
        })

        movieDetailViewModel.onLoadingMovie.observe(viewLifecycleOwner, Observer {
            if (it) {
                loading_movie_header.visibility = View.VISIBLE
                movie_detail_loader.visibility = View.VISIBLE
                view_pager.visibility = View.GONE
            } else {
                loading_movie_header.visibility = View.GONE
                movie_detail_loader.visibility = View.GONE
                view_pager.visibility = View.VISIBLE
            }
        })

        movieDetailViewModel.onErrorMovie.observe(viewLifecycleOwner, Observer {
            if (it) {
                loading_movie_header.visibility = View.GONE
                movie_detail_loader.visibility = View.GONE
                movieDetailViewModel.onErrorMovie.postValue(false)
            }
        })


    }

    private fun initTabs() {
        movie_detail_tab_layout.setupWithViewPager(view_pager)
        view_pager.adapter = pagerAdapter
        movie_detail_tab_layout.getTabAt(0)?.setIcon(R.drawable.ic_movie_black_24dp)
        movie_detail_tab_layout.getTabAt(0)?.text = getString(R.string.movie)
        movie_detail_tab_layout.getTabAt(1)?.setIcon(R.drawable.ic_videocam_black_24dp)
        movie_detail_tab_layout.getTabAt(1)?.text = getString(R.string.cast_amp_crew)
    }

    private fun initView(movie: Movie) {
        getMovieBackground(movie)
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
                    if (videos.isNotEmpty()) youtubePlayer.loadVideo(videos[0].key)
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
            GridLayoutManager(context, getSpanCount(movie.genres.size))
        genreAdapter.genres = movie.genres
        genreAdapter.notifyDataSetChanged()
    }

    private fun getSpanCount(size: Int): Int {
        if (size < MAX_SPAN_COUNT) {
            return if (size >= 1) size else MIN_SPAN_COUNT
        }
        return MAX_SPAN_COUNT
    }


    private fun getMovieBackground(movie: Movie) {

        Picasso.get()
            .load(TMDB_IMAGES_PATH + movie.backdrop_path)
            .transform(BlurTransformation(context, 15, 1))
            .fit()
            .into(movie_backdrop)
    }

    override fun onStop() {
        movieDetailViewModel.movie.postValue(null)
        movie_title.text = ""
        release.text = ""
        movie_rating.rating = 0F
        genreAdapter.genres = listOf()
        genreAdapter.notifyDataSetChanged()
        super.onStop()
    }

}

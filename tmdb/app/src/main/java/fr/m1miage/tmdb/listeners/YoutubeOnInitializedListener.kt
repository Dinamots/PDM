package fr.m1miage.tmdb.listeners

import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.api.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers

class YoutubeOnInitializedListener(val movie: Movie, val haveVideo: (Boolean) -> Unit) : YouTubePlayer.OnInitializedListener {
    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        p2: Boolean
    ) {
        val disposable = RetrofitManager.tmdbAPI.getVideos(movie.id.toLong())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { search ->
                    println(search)
                    val videos = search.results
                    haveVideo(videos.isNotEmpty())
                    if (videos.isNotEmpty()) player?.loadVideo(videos[0].key)
                },
                { err -> println(err) }
            )

    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {
    }
}
package fr.m1miage.tmdb.listeners.button

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import fr.m1miage.tmdb.activities.MovieDetailActivity
import fr.m1miage.tmdb.utils.MOVIE_ID_EXTRAS_KEY


class OnClickListenerMovie(val context: Context, val movieId: Int) : View.OnClickListener {
    override fun onClick(v: View?) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_ID_EXTRAS_KEY, movieId)
        context.startActivity(intent)
    }

}
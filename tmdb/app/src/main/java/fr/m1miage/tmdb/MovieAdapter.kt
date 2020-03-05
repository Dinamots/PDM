package fr.m1miage.tmdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.squareup.picasso.Picasso
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.utils.TMDB_IMAGES_PATH
import kotlinx.android.synthetic.main.movie_element.view.*

open class MovieAdapter(var movies: List<MovieResponse>, val listener: (MovieResponse) -> Unit) :
    Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_element, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]

        Picasso.get().load(TMDB_IMAGES_PATH + movie.poster_path).into(holder.movieImg)
        holder.movieRating.rating = movie.vote_average.toFloat() / 2
        holder.movieName.text = movie.title

        holder.itemView.setOnClickListener { listener(movie) }
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieImg = itemView.movie_img
        var movieRating = itemView.movie_rating
        var movieName = itemView.movie_name
    }

}
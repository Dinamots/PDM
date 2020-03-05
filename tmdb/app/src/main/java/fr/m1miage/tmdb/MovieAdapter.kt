package fr.m1miage.tmdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.squareup.picasso.Picasso
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.utils.TMDB_IMAGES_PATH
import kotlinx.android.synthetic.main.movie_element.view.*
import kotlinx.android.synthetic.main.movie_recycler_header.view.*

open class MovieAdapter(
    var movies: List<MovieResponse>,
    var headerStr: String,
    val listener: (MovieResponse) -> Unit
) : Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER: Int = 0
        const val TYPE_ITEM: Int = 1
    }

    override fun getItemViewType(position: Int): Int =
        if (position == 0) TYPE_HEADER else TYPE_ITEM

    override fun getItemCount(): Int = movies.size

    private fun getLayoutId(viewType: Int): Int =
        if (viewType == TYPE_HEADER) R.layout.movie_recycler_header else R.layout.movie_element

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(getLayoutId(viewType), parent, false)
        return if(viewType == TYPE_ITEM) ItemViewHolder(view) else HeaderViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemViewHolder) {
            initItemViewHolder(position, holder)
        }

        if(holder is HeaderViewHolder) {
            initHeaderViewHolder(holder)
        }

    }

    private fun initHeaderViewHolder(holder: HeaderViewHolder) {
        holder.textView.text = headerStr
    }

    private fun initItemViewHolder(position: Int, holder: ItemViewHolder) {
        val movie = movies[position]

        Picasso.get()
            .load(TMDB_IMAGES_PATH + if (movie.poster_path !== "") movie.poster_path else movie.backdrop_path)
            .fit()
            .into(holder.movieImg)
        holder.movieRating.rating = movie.vote_average.toFloat() / 2
        holder.movieName.text = movie.title

        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieImg: AppCompatImageView = itemView.movie_img
        var movieRating: RatingBar = itemView.movie_rating
        var movieName: AppCompatTextView = itemView.movie_name
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: AppCompatTextView = itemView.movie_recycler_header_text
    }


}
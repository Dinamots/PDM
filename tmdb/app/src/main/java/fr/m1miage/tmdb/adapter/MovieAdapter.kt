package fr.m1miage.tmdb.adapter

import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.ToggleButton
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.squareup.picasso.Picasso
import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.api.model.MovieResponse
import fr.m1miage.tmdb.listeners.button.FavoriteButtonCheckChangeListener
import fr.m1miage.tmdb.utils.*
import fr.m1miage.tmdb.utils.extension.getFavorites
import kotlinx.android.synthetic.main.movie_element.view.*
import kotlinx.android.synthetic.main.movie_element.view.movie_element_button_favorite
import kotlinx.android.synthetic.main.movie_recycler_header.view.*

open class MovieAdapter(
    var movies: MutableList<MovieResponse>,
    var headerStr: String?,
    private val preferences: SharedPreferences?,
    val listener: (MovieResponse) -> Unit,
    val favoriteListener: (MovieResponse, MovieAdapter) -> Unit


) : Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER: kotlin.Int = 0
        const val TYPE_ITEM: kotlin.Int = 1
        const val TYPE_LOADING: kotlin.Int = 2
    }

    override fun getItemViewType(position: kotlin.Int): kotlin.Int {
        return if (position == 0 && headerStr != null) TYPE_HEADER else TYPE_ITEM

    }

    override fun getItemCount(): kotlin.Int = movies.size

    private fun getLayoutId(viewType: kotlin.Int): kotlin.Int =
        if (viewType == TYPE_HEADER) R.layout.movie_recycler_header else R.layout.movie_element

    override fun onCreateViewHolder(parent: ViewGroup, viewType: kotlin.Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(getLayoutId(viewType), parent, false)
        return if (viewType == TYPE_ITEM) ItemViewHolder(view) else HeaderViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: kotlin.Int) {
        if (holder is ItemViewHolder) {
            initItemViewHolder(position, holder)
        }

        if (holder is HeaderViewHolder) {
            initHeaderViewHolder(holder)
        }

    }

    private fun initHeaderViewHolder(holder: HeaderViewHolder) {
        holder.textView.text = headerStr
    }

    open fun initItemViewHolder(position: kotlin.Int, holder: ItemViewHolder) {
        val movie = movies[position]

        Picasso.get()
            .load(TMDB_IMAGES_PATH + if (movie.poster_path !== "") movie.poster_path else movie.backdrop_path)
            .fit()
            .into(holder.movieImg)
        holder.movieRating.rating = movie.vote_average.toFloat() / 2
        holder.movieName.text = movie.title
        holder.movieImg.setOnClickListener { listener(movie) }
        holder.itemView.setOnClickListener { listener(movie) }
        holder.favoriteButton.setOnCheckedChangeListener(FavoriteButtonCheckChangeListener())
        holder.favoriteButton.setOnClickListener { favoriteListener(movie, this) }
        toggleFavoriteButton(movie, holder)
    }

    private fun getShareIntent(movieResponse: MovieResponse): Intent {
        return Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, movieResponse.title)
            type = "text/plain"
        }
    }

    private fun toggleFavoriteButton(
        movie: MovieResponse,
        holder: ItemViewHolder
    ) {
        val favorites: Favorites? = preferences?.getFavorites()
        if ((favorites?.movies?.find { it.id == movie.id } != null
                    && !holder.favoriteButton.isChecked)
            || (favorites?.movies?.find { it.id == movie.id } == null
                    && holder.favoriteButton.isChecked)
        ) {
            holder.favoriteButton.toggle()
        }
    }


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieImg: ImageButton = itemView.movie_img
        var movieRating: RatingBar = itemView.movie_rating
        var movieName: AppCompatTextView = itemView.movie_name
        val favoriteButton: ToggleButton = itemView.movie_element_button_favorite
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: AppCompatTextView = itemView.movie_recycler_header_text
    }


}
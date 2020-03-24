package fr.m1miage.tmdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.adapter.GenreAdapter.*
import fr.m1miage.tmdb.api.model.Genre
import kotlinx.android.synthetic.main.genre_element.view.*

class GenreAdapter(var genres: List<Genre>) : RecyclerView.Adapter<GenreHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.genre_element, parent, false)
        return GenreHolder(view)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: GenreHolder, position: Int) {
        val genre = genres[position]

        holder.chip.text = genre.name
    }

    class GenreHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chip = itemView.genre_chip
    }
}



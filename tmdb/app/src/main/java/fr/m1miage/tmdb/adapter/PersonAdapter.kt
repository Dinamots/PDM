package fr.m1miage.tmdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.m1miage.tmdb.R
import fr.m1miage.tmdb.adapter.PersonAdapter.PersonViewHolder
import fr.m1miage.tmdb.api.model.Person
import fr.m1miage.tmdb.utils.ANONYMOUS_IMG_PATH
import fr.m1miage.tmdb.utils.TMDB_IMAGES_PATH
import fr.m1miage.tmdb.utils.extension.getImgLink
import fr.m1miage.tmdb.utils.extension.isCast
import kotlinx.android.synthetic.main.person_element.view.*

class PersonAdapter(var persons: MutableList<Person>, val onClick: (Person) -> Unit) :
    RecyclerView.Adapter<PersonViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.person_element, parent, false)
        return PersonViewHolder(view)
    }

    override fun getItemCount(): Int = persons.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = persons[position]


        Picasso
            .get()
            .load(person.getImgLink())
            .fit()
            .into(holder.personImg)

        holder.personName.text = person.name
        holder.personJob.text =
            if (person.isCast()) person.character else person.job

        holder.itemView.setOnClickListener { onClick(person) }
    }

    class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personImg = itemView.person_img
        val personName = itemView.person_name
        val personJob = itemView.person_job
    }

}



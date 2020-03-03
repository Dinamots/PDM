package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.clerc.myapplication.kotlin.Student
import fr.clerc.myapplication.kotlin.model.Feature
import kotlinx.android.synthetic.main.agence_layout.view.*
import java.util.*


open class Adapter(var features: List<Feature>, val listener: (Feature) -> Unit) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.agence_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return features.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feature: Feature = features[position]

        holder.nomAgence.text = feature.properties.name.toUpperCase(Locale.FRANCE)
        holder.streetNumber.text = feature.properties.street.toUpperCase(Locale.FRANCE)
        holder.postalCodeAndCity.text =
            "${feature.properties.postalCode} - ${feature.properties.city}".toUpperCase(
                Locale.FRANCE
            )

        holder.itemView.setOnClickListener { listener(feature) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        var nomAgence = itemView.nom_agence
        var streetNumber = itemView.street_number
        var postalCodeAndCity = itemView.postal_code_and_city
    }

}
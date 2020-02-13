package com.example.recyclerviewexemple

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.clerc.myapplication.kotlin.Student
import kotlinx.android.synthetic.main.student_layout.view.*


open class Adapter(val students: List<Student>, val listener: (Student) -> Unit) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.student_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student: Student = students[position]

        holder.lastName.text = student.lastName
        holder.firstName.text = student.firstName

        holder.itemView.setOnClickListener { listener(student) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {

        var firstName = itemView.nom
        var lastName = itemView.prenom
        var imageView = itemView.student_img


    }

}
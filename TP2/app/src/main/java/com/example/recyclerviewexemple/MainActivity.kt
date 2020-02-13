package com.example.recyclerviewexemple

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.clerc.myapplication.kotlin.StudentContent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        student_recycler_view.layoutManager = LinearLayoutManager(this)

        val adapter = Adapter(StudentContent.ITEMS) {student -> run {
            val intent = Intent(this, StudentDetailActivity::class.java )
            intent.putExtra("student",student)
            startActivity(intent)
        } }
        student_recycler_view.adapter = adapter

    }
}

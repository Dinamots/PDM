package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.clerc.myapplication.kotlin.Student
import fr.clerc.myapplication.kotlin.model.Feature
import kotlinx.android.synthetic.main.student_detail_layout.*
import kotlinx.android.synthetic.main.student_detail_layout.view.*

class AgenceDetailActivity : AppCompatActivity() {
    private var feature: Feature? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_detail_layout)
        feature = intent.extras!!["feature"] as Feature
        initStudentDetails()

    }

    private fun initStudentDetails() {
        println(feature)
    }
}
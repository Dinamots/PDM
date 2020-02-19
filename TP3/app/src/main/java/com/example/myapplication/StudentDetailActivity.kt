package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.clerc.myapplication.kotlin.Student
import kotlinx.android.synthetic.main.student_detail_layout.*
import kotlinx.android.synthetic.main.student_detail_layout.view.*

class StudentDetailActivity: AppCompatActivity() {
    private var student: Student? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_detail_layout)
        student = intent.extras!!["student"] as Student
        initStudentDetails()

    }

    private fun initStudentDetails() {
        first_name.text = student?.firstName
        last_name.text = student?.lastName
        gender.text = student?.gender
        age.text = student?.age.toString()
    }
}
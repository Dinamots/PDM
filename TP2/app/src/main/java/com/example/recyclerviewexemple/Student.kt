package fr.clerc.myapplication.kotlin

import java.io.Serializable

data class Student(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val age: Int
): Serializable
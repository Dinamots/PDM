package fr.clerc.myapplication.kotlin.model

import java.io.Serializable
import java.util.*

data class Geometry(
        val type: String,
        val coordinates: ArrayList<Float>
) : Serializable
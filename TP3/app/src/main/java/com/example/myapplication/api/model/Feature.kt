package fr.clerc.myapplication.kotlin.model

import java.io.Serializable

data class Feature(
    val type: String,
    val properties: Properties,
    val geometry: Geometry

): Serializable
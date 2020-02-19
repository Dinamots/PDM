package fr.clerc.myapplication.kotlin.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class FeatureCollection(
        val type: String,
        @SerializedName("features")
        val featureList: ArrayList<Feature>
)
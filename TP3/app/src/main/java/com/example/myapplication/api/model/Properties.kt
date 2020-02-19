package fr.clerc.myapplication.kotlin.model

import com.google.gson.annotations.SerializedName

data class Properties(
        val id: Int,
        @SerializedName("NOM")
        val name: String,
        @SerializedName("RUE")
        val street: String,
        @SerializedName("CODEPOSTAL")
        val postalCode: Int,
        @SerializedName("COMMUNE")
        val city: String,
        @SerializedName("TYPE")
        val type: String,
        @SerializedName("CODE")
        val code: Int,
        @SerializedName("LaMetro")
        val isMetro: Boolean,
        @SerializedName("LeGresivaudan")
        val isGresivaudan: Boolean,
        @SerializedName("PaysVoironnais")
        val isPaysVoironnais: Boolean

)
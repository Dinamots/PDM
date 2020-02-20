package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import fr.clerc.myapplication.kotlin.model.Feature
import kotlinx.android.synthetic.main.agence_detail_layout.*

class AgenceDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var feature: Feature
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agence_detail_layout)
        feature = intent.extras!!["feature"] as Feature
        initStudentDetails()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initStudentDetails() {
        postal_code_and_city.text =
            "${feature.properties.postalCode}-${feature.properties.city}".toUpperCase()
        adress.text = feature.properties.street
        agency_name.text = feature.properties.name
        is_metro.text = if (feature.properties.isMetro) "Appartient à la métro" else ""
        type.text = feature.properties.type
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        println(feature.geometry.coordinates)
        val featureLocation = LatLng(
            feature.geometry.coordinates[1].toDouble(),
            feature.geometry.coordinates[0].toDouble()
        )
        mMap.addMarker(MarkerOptions().position(featureLocation).title(feature.properties.name))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(featureLocation))
        mMap.setMinZoomPreference(15.0F)
    }
}
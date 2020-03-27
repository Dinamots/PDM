package fr.m1miage.tmdb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import fr.m1miage.tmdb.ui.home.HomeViewModel

class SplashActivity : AppCompatActivity() {
    private val splashTime = 300L
    private lateinit var handler: Handler
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler = Handler()
        handler.postDelayed({
            goToMainActivity()
        }, splashTime)
    }

    private fun goToMainActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

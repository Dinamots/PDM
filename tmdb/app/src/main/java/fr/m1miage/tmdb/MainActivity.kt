package fr.m1miage.tmdb

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawer_layout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        // SearchView((baseContext as MainActivity).supportActionBar?.themedContext ?: baseContext)
        val searchView: SearchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener (object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                println("ici")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                println("la")
                return false
            }
        })
        return true
    }

    /* override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        val searchView = SearchView((context as MainActivity).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.action_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        searchView.setOnClickListener {view ->  }
    } */

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        println(intent)
    }


}

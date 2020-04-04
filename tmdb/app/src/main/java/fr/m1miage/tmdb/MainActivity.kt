package fr.m1miage.tmdb

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import fr.m1miage.tmdb.ui.home.HomeViewModel
import fr.m1miage.tmdb.ui.search.SearchViewModel
import fr.m1miage.tmdb.utils.snack
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    val searchViewModel: SearchViewModel by viewModels()
    val homeViewModel: HomeViewModel by viewModels()
    var currentNav: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        setContentView(R.layout.splash)
        homeViewModel.fetchAll()
        Handler().postDelayed({
            setContentView(R.layout.activity_main)
            val navController = findNavController(R.id.nav_host_fragment)
            val toolbar: Toolbar = findViewById(R.id.toolbar)

            initConnectivityManager()
            setSupportActionBar(toolbar)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                currentNav = destination.id
            }
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_home, R.id.nav_favorite, R.id.nav_search
                ), drawer_layout
            )

            setupActionBarWithNavController(navController, appBarConfiguration)
            nav_view.setupWithNavController(navController)
        }, 300L)
    }

    private fun initConnectivityManager() {
        val navController = findNavController(R.id.nav_host_fragment)
        val self = this
        val connectivityManager =
            baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    homeViewModel.fetchAll()
                    if (currentNav != 0) {
                        val nav = currentNav
                        runOnUiThread { navController.popBackStack();navController.navigate(nav) }
                    }
                    ConnectionManager.isConnected.postValue(true)
                }

                override fun onLost(network: Network?) {
                    snack(self.findViewById(android.R.id.content)!!, "Internet connection lost")
                    ConnectionManager.isConnected.postValue(false)
                }


            })
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu.findItem(R.id.search)
        val searchView: SearchView = search.actionView as SearchView
        searchView.isIconified = false
        searchView.setOnCloseListener { true }
        setOnActionExpandListenerOnSearchItem(search)
        setQueryListenerOnSearchView(searchView)
        return true
    }

    private fun setQueryListenerOnSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchViewModel.searchSting.value = query
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun setOnActionExpandListenerOnSearchItem(search: MenuItem) {
        search.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                navigate(R.id.nav_search)
                item?.isChecked = true
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                navigate(R.id.nav_home)
                return true
            }

        })
    }


    private fun navigate(destinationId: Int) {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(destinationId)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        if (ConnectionManager.isConnected.value == false) {
            navController.navigate(R.id.nav_home)
        }
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}

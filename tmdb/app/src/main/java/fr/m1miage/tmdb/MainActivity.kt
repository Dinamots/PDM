package fr.m1miage.tmdb

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import fr.m1miage.tmdb.api.RetrofitManager
import fr.m1miage.tmdb.ui.home.HomeViewModel
import fr.m1miage.tmdb.ui.movie.MovieDetailViewModel
import fr.m1miage.tmdb.ui.person.PersonViewModel
import fr.m1miage.tmdb.ui.search.SearchViewModel
import fr.m1miage.tmdb.utils.changeLanguage
import fr.m1miage.tmdb.utils.extension.getLocale
import fr.m1miage.tmdb.utils.snack
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    val searchViewModel: SearchViewModel by viewModels()
    val homeViewModel: HomeViewModel by viewModels()
    val movieDetailViewModel: MovieDetailViewModel by viewModels()
    val personViewModel: PersonViewModel by viewModels()
    var navigateUp: Boolean = false
    var currentNav: Int = 0
    lateinit var preferences: SharedPreferences
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = this.getPreferences(Context.MODE_PRIVATE)!!
        changeLanguage(preferences.getLocale(), preferences, resources)
        RetrofitManager.preferences = preferences
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        setContentView(R.layout.splash)
        homeViewModel.fetchAll()
        Handler().postDelayed({ main() }, 300L)
    }

    private fun main() {
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        initConnectivityManager()
        setSupportActionBar(toolbar)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            onDestinationChanged(destination.id)
        }
        appBarConfiguration = AppBarConfiguration(getNavigationList(), drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
        nav_view.setNavigationItemSelectedListener { item -> onNavigationItemSelected(item) }
    }

    private fun getNavigationList() = setOf(R.id.nav_home, R.id.nav_favorite, R.id.nav_search)

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.languages -> onLanguages()
            else -> {
                navigate(item.itemId)
                drawer_layout.closeDrawer(nav_view)
            }
        }
        return true
    }

    private fun onLanguages() {
        val bottomSheet = LanguagesBottomSheet(
            preferences,
            resources
        ) {
            finish()
            startActivity(intent)
        }
        bottomSheet.show(supportFragmentManager, "Languages")
    }

    private fun initConnectivityManager() {
        val navController = findNavController(R.id.nav_host_fragment)
        val self = this
        val connectivityManager =
            baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(
                networkCallback(navController, self)
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun networkCallback(
        navController: NavController,
        self: MainActivity
    ): ConnectivityManager.NetworkCallback {
        return object :
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
                snack(
                    self.findViewById(android.R.id.content)!!,
                    getString(R.string.connection_lost)
                )
                ConnectionManager.isConnected.postValue(false)
            }


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
        val self = this
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchViewModel.searchSting.postValue(query)
                return true
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
        navController.navigate(destinationId)
    }

    override fun onSupportNavigateUp(): Boolean {
        navigateUp = true
        if (ConnectionManager.isConnected.value == false) {
            navController.navigate(R.id.nav_home)
            return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        }
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun onDestinationChanged(destination: Int) {
        if (!navigateUp) {
            storingIdsOnStack(currentNav)
        }

        if (navigateUp) {
            popIdsStacks(destination)
            navigateUp = false
        }

        currentNav = destination
    }


    private fun popIdsStacks(destinationId: Int) {
        when (destinationId) {
            R.id.nav_person -> personViewModel.personId.postValue(personViewModel.getId())
            R.id.nav_movie_detail -> movieDetailViewModel.movieId.postValue(movieDetailViewModel.getId())
        }
    }

    private fun storingIdsOnStack(destinationId: Int) {
        when (destinationId) {
            R.id.nav_person -> personViewModel.storeId()
            R.id.nav_movie_detail -> movieDetailViewModel.storeId()
        }
    }

    override fun onBackPressed() {
    }
}

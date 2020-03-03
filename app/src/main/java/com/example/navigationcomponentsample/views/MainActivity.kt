package com.example.navigationcomponentsample.views

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.navigationcomponentsample.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout:DrawerLayout
    lateinit var navController: NavController
    lateinit var navigationView:NavigationView

   @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       setupNavigation()
}

    @Override
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout)
    }

    @Override
    override fun onBackPressed() = if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()

    @Override
    override fun onNavigationItemSelected(p0: MenuItem):Boolean {

        p0.isChecked = true
        drawerLayout.closeDrawers()

        when(p0.itemId){
            R.id.email ->showMessage(getString(R.string.email_slected))
                /*if(navController.currentDestination?.id != R.id.locationdetailsfragment){
                    val action =
                        LocationListFragmentDirections.locationlisttodetail(getString(R.string.my_app_default_argument))
                    navController.navigate(action)}*/
            R.id.favourites -> showMessage(getString(R.string.fav_selected))
            R.id.directions -> showMessage(getString(R.string.direction_selected))

        }

        return true
    }

    private fun showMessage(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }

    private fun setupNavigation(){
        toolbar  = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)
        navigationView.setNavigationItemSelectedListener(this)

    }

}

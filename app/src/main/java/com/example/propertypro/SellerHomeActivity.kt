package com.example.propertypro

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.example.propertypro.databinding.ActivitySellerHomeBinding
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class SellerHomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivitySellerHomeBinding
    private lateinit var drawerLayout: androidx.drawerlayout.widget.DrawerLayout
    private lateinit var navigationView: NavigationView
    private val propertyRepository = PropertyRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding using the generated binding class
        binding = ActivitySellerHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize DrawerLayout and NavigationView
        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set up the ActionBarDrawerToggle
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set the navigation item selected listener
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle Home click
                // You can replace this with the action you want to perform
            }
            R.id.nav_listings -> {
                // Call getProperties() from the repository
                propertyRepository.getProperties()
            }
            R.id.nav_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.nav_logout -> {
                // Handle Logout click
            }
        }

        // Close the drawer
        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    // Override onBackPressed to close the drawer if it's open
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}

class PropertyRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getProperties(): Task<QuerySnapshot> {
        return db.collection("properties").get()
    }
}

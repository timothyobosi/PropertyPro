package com.example.propertypro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.propertypro.databinding.ActivitySellerHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SellerHomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivitySellerHomeBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private val propertyRepository = PropertyRepository()
    private lateinit var auth: FirebaseAuth // Add this line

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding using the generated binding class
        binding = ActivitySellerHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

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

        // Check if user is signed in (not null), and update UI accordingly
        val currentUser: FirebaseUser? = auth.currentUser
        updateUI(currentUser)
    }

    // Add this function to update UI based on user authentication status
    private fun updateUI(currentUser: FirebaseUser?) {
        // Example: If the user is logged in, show their email in a TextView
        if (currentUser != null) {
            val userEmail = currentUser.email
            // Update UI with the user's information
        } else {
            // If the user is not logged in, redirect them to the login screen or handle as needed
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    // Add this function to handle logout
    private fun signOut() {
        auth.signOut()
        updateUI(null)
    }

    // Override onBackPressed to close the drawer if it's open
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle Home click
                // You can replace this with the action you want to perform
            }
            R.id.nav_listings -> {
                // Fetch properties from Firestore
                propertyRepository.getProperties().addOnSuccessListener { querySnapshot ->
                    val properties = querySnapshot.toObjects(Propertys::class.java)

                    // Set up RecyclerView
                    val recyclerView: RecyclerView = findViewById(R.id.recyclerViewProperties)
                    val adapter = PropertyAdapter(properties)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this)
                }.addOnFailureListener { exception ->
                    // Handle failure
                    Log.e(TAG, "Error getting properties", exception)
                }
            }
            R.id.nav_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.nav_logout -> {
                // Handle Logout click
                signOut()
            }
            R.id.nav_account, R.id.nav_profile -> {
                // Handle Account or Profile click
                startActivity(Intent(this, UserProfileActivity::class.java))

            }
            // Add more cases for other menu items as needed
        }

        // Close the drawer
        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    companion object {
        private const val TAG = "SellerHomeActivity"
    }
}

package com.example.propertypro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_1) // Update layout reference here

        val searchView = findViewById<android.widget.SearchView>(R.id.searchBar) // Note: Use SearchView here
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener { // Correct import
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.equals("explore", ignoreCase = true) == true) {
                    openPropertyListBottomSheet()
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text change if needed
                return true
            }
        })
    }

    private fun openPropertyListBottomSheet() {
        val bottomSheetFragment = PropertyListBottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }
}

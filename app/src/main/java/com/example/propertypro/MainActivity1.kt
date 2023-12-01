package com.example.propertypro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView

class MainActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_1) // Update layout reference here

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.equals("explore", ignoreCase = true)) {
                    openPropertyListBottomSheet()
                    return true
                } else if (query.equals("contribute", ignoreCase = true)) {
                    openContributionForm()
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

    private fun openContributionForm() {
        val contributionFormFragment = ContributionFormFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, contributionFormFragment)
            .addToBackStack(null)
            .commit()
    }

}

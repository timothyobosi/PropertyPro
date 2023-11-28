package com.example.propertypro

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.propertypro.databinding.ActivityBuyerPropertyListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class BuyerPropertyListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuyerPropertyListBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var propertyListView: ListView
    private lateinit var propertyList: MutableList<String>
    private lateinit var propertyIdList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerPropertyListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        propertyListView = binding.propertyListView
        propertyList = mutableListOf()
        propertyIdList = mutableListOf()

        propertyListView.setOnItemClickListener { _, _, position, _ ->
            val propertyId = propertyIdList[position]
            val intent = Intent(this, BuyerPropertyDetailsActivity::class.java)
            intent.putExtra("PROPERTY_ID", propertyId)
            startActivity(intent)
        }

        loadProperties()
    }

    private fun loadProperties() {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val propertiesRef = database.reference.child("properties")
            propertiesRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    propertyList.clear()
                    propertyIdList.clear()

                    for (propertySnapshot in dataSnapshot.children) {
                        val property = propertySnapshot.getValue(Property::class.java)
                        if (property != null) {
                            propertyList.add("Property Number: ${property.propertyNumber}, Price: ${property.price}")
                            propertyIdList.add(propertySnapshot.key!!)
                        }
                    }

                    val adapter = ArrayAdapter(
                        this@BuyerPropertyListActivity,
                        android.R.layout.simple_list_item_1,
                        propertyList
                    )
                    propertyListView.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors
                }
            })
        }
    }
}

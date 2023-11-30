package com.example.propertypro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PropertyListBottomSheetFragment : DialogFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var propertyListAdapter: PropertyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_property_list_bottom_sheet, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewProperties)

        // Set up RecyclerView and adapter
        propertyListAdapter = PropertyListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = propertyListAdapter

        // Populate the adapter with sample data
        val sampleProperties = getSampleProperties()
        propertyListAdapter.submitList(sampleProperties)

        return view
    }

    private fun getSampleProperties(): List<PropertyItem> {
        // Replace this with your actual data retrieval logic
        // For now, let's create some sample properties
        return listOf(
            PropertyItem("Property 1", "Description 1", R.drawable.property_image_1),
            PropertyItem("Property 2", "Description 2", R.drawable.property_image_2),
            PropertyItem("Property 3", "Description 3", R.drawable.property_image_3)
        )
    }
}

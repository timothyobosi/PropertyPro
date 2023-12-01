package com.example.propertypro

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ContributionFormFragment : Fragment() {

    private lateinit var editTextTitleNumber: EditText
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var imageViewProperty: ImageView
    private lateinit var buttonChooseImage: Button
    private lateinit var buttonSubmitContribution: Button

    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contribution_form, container, false)

        // Initialize UI elements
        editTextTitleNumber = view.findViewById(R.id.editTextTitleNumber)
        editTextTitle = view.findViewById(R.id.editTextTitle)
        editTextDescription = view.findViewById(R.id.editTextDescription)
        imageViewProperty = view.findViewById(R.id.imageViewProperty)
        buttonChooseImage = view.findViewById(R.id.buttonChooseImage)
        buttonSubmitContribution = view.findViewById(R.id.buttonSubmitContribution)

        // Set click listeners
        buttonChooseImage.setOnClickListener { openGallery() }
        buttonSubmitContribution.setOnClickListener {
            submitContribution()
        }
        return view
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            imageViewProperty.setImageURI(selectedImageUri)
        }
    }

    private fun submitContribution() {
        // Get entered details
        val titleNumber = editTextTitleNumber.text.toString()
        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()

        // Validate required fields
        if (titleNumber.isBlank() || title.isBlank() || description.isBlank()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }
    }
}

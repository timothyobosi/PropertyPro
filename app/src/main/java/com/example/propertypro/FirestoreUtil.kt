package com.example.propertypro

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreUtil {

    private val db = FirebaseFirestore.getInstance()

    // Function to fetch details of a specific property from Firestore
    fun getPropertyDetails(propertyId: String): Task<DocumentSnapshot> {
        return db.collection("properties").document(propertyId).get()
    }
}
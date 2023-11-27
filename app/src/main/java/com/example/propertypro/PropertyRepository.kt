package com.example.propertypro

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class PropertyRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getProperties(): Task<QuerySnapshot> {
        return db.collection("properties").get()
    }
}
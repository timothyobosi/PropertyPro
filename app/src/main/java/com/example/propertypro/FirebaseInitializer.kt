package com.example.propertypro

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseInitializer : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Firestore
        val db = FirebaseFirestore.getInstance()
    }
}
package com.example.propertypro

import java.io.Serializable

data class Propertys(
    val id: String,
    val title: String,
    val description: String,
    val price: Double
    // Add more properties as needed
): Serializable

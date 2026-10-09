package com.example.listycity

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CityRepository {
    private val db = Firebase.firestore
    private val citiesRef = db.collection("cities")
    private val _cities = mutableStateListOf(
        City("","Edmonton", "AB"),
        City("","Vancouver", "BC"),
        City("","Toronto", "ON")
    )

    val cities: List<City>
        get() = _cities

    fun addCity(city: City) {
        /**
         * The following code used to generate a document ID was based on examples provided
         * by Firebase Documentation at https://firebase.google.com/docs/firestore/enterprise/add-data-core#kotlin_7
         */
        val idRef = citiesRef.document()
        idRef.set(city)
    }

    fun updateCity(oldCity: City, updatedCity: City) {
        citiesRef.document(oldCity.documentID).set(updatedCity)
    }

    fun deleteCity(city:City) {
        citiesRef.document(city.documentID).delete()
    }

    init {
        citiesRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            _cities.clear()
            snapshot?.documents?.forEach { document ->
                val city = document.toObject(City::class.java)
                if (city != null) {
                    _cities.add(city)
                }
            }
        }
    }
}
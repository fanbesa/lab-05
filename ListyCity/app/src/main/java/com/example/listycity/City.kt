package com.example.listycity

import com.google.firebase.firestore.DocumentId

data class City(
    @set:DocumentId
    var documentID: String = "",
    val name: String = "",
    val province: String = "",
)
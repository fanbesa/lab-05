package com.example.listycity

import com.google.firebase.firestore.DocumentId

data class City(
    // https://stackoverflow.com/questions/46995080/how-do-i-get-the-document-id-for-a-firestore-document-using-kotlin-data-classes
    // @DocumentId
    val name: String = "",
    val province: String = "",
    // val documentID: String
)
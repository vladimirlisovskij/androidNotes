package com.example.notes.data.dataBase.entitie

import android.os.Parcelable
import com.example.notes.domain.enitites.NoteEntity
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employee2(
    val id: String,
    val header: String,
    val desc: String,
    val body: String,
    val image: List<String>,
    val creationDate: String,
    val lastEditDate: String
) : Parcelable


fun QueryDocumentSnapshot.toEmployee(): Employee2 {
    return Employee2(
        id = id as String,
        header = this["header"] as String,
        desc = this["desc"] as String,
        body = this["body"] as String,
        image = this["image"] as List<String>,
        creationDate = this["creationDate"] as String,
        lastEditDate = this["lastEditDate"] as String,
    )
}

fun Employee2.toDomain(): NoteEntity {
    return NoteEntity(
        id = id,
        header = header,
        desc = desc,
        body = body,
        image = image,
        creationDate = creationDate,
        lastEditDate = lastEditDate
    )
}




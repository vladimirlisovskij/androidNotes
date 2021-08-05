package com.example.notes.cleanArchitecture.presenter.entities

import android.os.Parcelable
import com.example.notes.cleanArchitecture.domain.enitites.NoteEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

@Parcelize
data class PresenterNoteEntity(
    var id: String,
    val header: String,
    val desc: String,
    val body: String,
    var image: List<String>,
    val creationDate: String,
    var lastEditDate: String
) : Parcelable

fun NoteEntity.toPresentation(): PresenterNoteEntity {
    return PresenterNoteEntity(
        id = id,
        header = header,
        desc = desc,
        body = body,
        image = image,
        creationDate = creationDate,
        lastEditDate = lastEditDate
    )
}

fun PresenterNoteEntity.toDomain(): NoteEntity {
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



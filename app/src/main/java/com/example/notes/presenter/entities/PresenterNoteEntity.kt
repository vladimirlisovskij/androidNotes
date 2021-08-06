package com.example.notes.presenter.entities

import android.os.Parcelable
import com.example.notes.domain.enitites.NoteEntity
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



package com.example.notes.presenter.entities

import android.os.Parcelable
import com.example.notes.domain.enitites.WidgetNoteEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class PresenterWidgetNoteEntity(
    var id: Long,
    val header: String,
    val desc: String,
    val body: String
) : Parcelable

fun WidgetNoteEntity.toPresentation(): PresenterWidgetNoteEntity {
    return PresenterWidgetNoteEntity(
        id = id,
        header = header,
        desc = desc,
        body = body
    )
}

fun PresenterWidgetNoteEntity.toWidgetDomain(): WidgetNoteEntity {
    return WidgetNoteEntity(
        id = id,
        header = header,
        desc = desc,
        body = body
    )
}
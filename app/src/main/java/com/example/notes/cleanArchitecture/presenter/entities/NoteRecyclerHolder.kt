package com.example.notes.cleanArchitecture.presenter.entities

import android.os.Parcelable
import com.example.notes.cleanArchitecture.domain.enitites.NoteEntity
import com.example.notes.cleanArchitecture.domain.enitites.WidgetNoteEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteRecyclerHolder (
    var id: Long,
    val header: String,
    val desc: String,
    val body: String,
    var image: List<String>,
    val creationDate: String,
    var lastEditDate: String
) : Parcelable

fun NoteEntity.toPresentation(): NoteRecyclerHolder {
    return NoteRecyclerHolder(
        id=this.id,
        header=this.header,
        desc=this.desc,
        body=this.body,
        image=this.image,
        creationDate=this.creationDate,
        lastEditDate=this.lastEditDate
    )
}

fun NoteRecyclerHolder.toDomain(): NoteEntity {
    return NoteEntity(
        id=this.id,
        header=this.header,
        desc=this.desc,
        body=this.body,
        image=this.image,
        creationDate=this.creationDate,
        lastEditDate=this.lastEditDate
    )
}

fun WidgetNoteEntity.toPresentation(): NoteRecyclerHolder {
    return NoteRecyclerHolder(
        id=this.id,
        header=this.header,
        desc=this.desc,
        body=this.body,
        image= listOf(),
        creationDate="",
        lastEditDate=""
    )
}

fun NoteRecyclerHolder.toWidgetDomain(): WidgetNoteEntity {
    return WidgetNoteEntity(
        id=this.id,
        header=this.header,
        desc=this.desc,
        body=this.body
    )
}



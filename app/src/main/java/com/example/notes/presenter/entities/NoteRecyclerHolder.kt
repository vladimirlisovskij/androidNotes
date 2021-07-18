package com.example.notes.presenter.entities

import android.os.Parcelable
import com.example.notes.domain.enitites.NoteEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteRecyclerHolder (
    val id: Long,
    val header: String,
    val desc: String,
    val body: String,
    var image: String
) : Parcelable

fun NoteEntity.toPresentation(): NoteRecyclerHolder {
    return NoteRecyclerHolder(
        id=this.id,
        header=this.header,
        desc=this.desc,
        body=this.body,
        image=this.image
    )
}

fun NoteRecyclerHolder.toDomain(): NoteEntity {
    return NoteEntity(
        id=this.id,
        header=this.header,
        desc=this.desc,
        body=this.body,
        image=this.image
    )
}
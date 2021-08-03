package com.example.notes.cleanArchitecture.data.dataBase.entitie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes.cleanArchitecture.domain.enitites.WidgetNoteEntity

@Entity
data class NoteEmployee(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val header: String,
    val desc: String,
    val body: String
)

fun WidgetNoteEntity.toData(): NoteEmployee {
    return NoteEmployee(
        id= this.id,
        header=this.header,
        desc=this.desc,
        body=this.body
    )
}

fun NoteEmployee.toDomain(): WidgetNoteEntity {
    return WidgetNoteEntity(
        id=this.id,
        header=this.header,
        desc=this.desc,
        body=this.body
    )
}
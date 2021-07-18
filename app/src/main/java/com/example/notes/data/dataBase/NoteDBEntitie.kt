package com.example.notes.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes.domain.enitites.NoteEntity

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val header: String,
    val desc: String,
    val body: String,
    val image: String
)

fun NoteEntity.toData(): Employee {
    return Employee(
        id= this.id,
        header=this.header,
        desc=this.desc,
        body=this.body,
        image=this.image
    )
}

fun Employee.toDomain(): NoteEntity {
    return NoteEntity(
        id=this.id,
        header=this.header,
        desc=this.desc,
        body=this.body,
        image=this.image
    )
}
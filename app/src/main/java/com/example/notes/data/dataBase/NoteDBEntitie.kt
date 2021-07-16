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
)

fun NoteEntity.toData(): Employee {
    return Employee(
        id= this.id.toLong(),
        header=this.header,
        desc=this.desc,
        body=this.body
    )
}

fun Employee.toDomain(): NoteEntity {
    return NoteEntity(
        id=this.id.toInt(),
        header=this.header,
        desc=this.desc,
        body=this.body
    )
}
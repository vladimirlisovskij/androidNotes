package com.example.notes.cleanArchitecture.data.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes.cleanArchitecture.domain.enitites.NoteEntity

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val header: String,
    val desc: String,
    val body: String,
    val image: List<String>,
    val creationDate: String,
    val lastEditDate: String
)

fun NoteEntity.toData(): Employee {
    return Employee(
        id= this.id,
        header=this.header,
        desc=this.desc,
        body=this.body,
        image=this.image,
        creationDate=this.creationDate,
        lastEditDate=this.lastEditDate
    )
}

fun Employee.toDomain(): NoteEntity {
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
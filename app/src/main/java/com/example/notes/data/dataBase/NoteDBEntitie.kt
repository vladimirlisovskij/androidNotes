package com.example.notes.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.notes.domain.enitites.NoteEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class ListStringConverter {
    @TypeConverter
    fun fromString(value: String): List<String> = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun fromList(value: List<String>): String = Gson().toJson(value)
}

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
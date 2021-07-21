package com.example.notes.domain.enitites

data class NoteEntity (
    var id: Long,
    val header: String,
    val desc: String,
    val body: String,
    val image: String,
    val creationDate: String,
    val lastEditDate: String
)

fun NoteEntity.replaceImage(newImage: String): NoteEntity {
    return NoteEntity(
        id=this.id,
        header=this.header,
        desc=this.desc,
        body=this.body,
        image=newImage,
        creationDate=this.creationDate,
        lastEditDate=this.lastEditDate
    )
}
package com.example.notes.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notes.data.dataBase.entitie.NoteEmployee

@Dao
interface WidgetNoteDao {
    @Query("select * from noteemployee where id=:id")
    fun getById(id: Long): NoteEmployee?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NoteEmployee)

    @Query("delete from noteemployee where id=:id")
    fun deleteById(id: Long)
}
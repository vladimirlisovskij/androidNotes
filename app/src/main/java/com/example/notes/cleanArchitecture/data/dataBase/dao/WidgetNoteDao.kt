package com.example.notes.cleanArchitecture.data.dataBase.dao

import androidx.room.*
import com.example.notes.cleanArchitecture.data.dataBase.entitie.NoteEmployee

@Dao
interface WidgetNoteDao {
    @Query("select * from noteemployee where id=:id")
    fun getById(id: Long): NoteEmployee?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NoteEmployee)

    @Query("delete from noteemployee where id=:id")
    fun deleteById(id: Long)
}
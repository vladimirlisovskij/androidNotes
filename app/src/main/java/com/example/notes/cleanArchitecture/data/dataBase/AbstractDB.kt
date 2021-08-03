package com.example.notes.cleanArchitecture.data.dataBase

import androidx.room.*
import com.example.notes.application.MainApplication
import com.example.notes.cleanArchitecture.data.dataBase.dao.EmployeeDao
import com.example.notes.cleanArchitecture.data.dataBase.dao.WidgetNoteDao
import com.example.notes.cleanArchitecture.data.dataBase.entitie.Employee
import com.example.notes.cleanArchitecture.data.dataBase.entitie.NoteEmployee
import com.google.gson.Gson

class ListStringConverter {
    @TypeConverter
    fun fromString(value: String): List<String> = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun fromList(value: List<String>): String = Gson().toJson(value)
}

@Database(entities = [Employee::class, NoteEmployee::class], version = 1)
@TypeConverters(ListStringConverter::class)
abstract class AbstractDB: RoomDatabase() {
    companion object {
        private const val DB_NAME: String = "notesDataBase"

        val instance: AbstractDB by lazy {
            Room.databaseBuilder(MainApplication.instance.applicationContext, AbstractDB::class.java, DB_NAME)
                .build()
        }
    }

    abstract fun employeeDAO(): EmployeeDao

    abstract fun widgetNoteDao(): WidgetNoteDao
}

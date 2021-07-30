package com.example.notes.cleanArchitecture.data.dataBase

import androidx.room.*
import com.example.notes.application.MainApplication
import com.google.gson.Gson

class ListStringConverter {
    @TypeConverter
    fun fromString(value: String): List<String> = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun fromList(value: List<String>): String = Gson().toJson(value)
}

@Database(entities = [Employee::class], version = 1)
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
}

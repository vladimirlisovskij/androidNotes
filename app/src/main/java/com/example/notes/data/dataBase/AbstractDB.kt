package com.example.notes.data.dataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notes.application.MainApplication
import com.example.notes.data.dataBase.entity.Employee
import com.example.notes.data.dataBase.entity.ListStringConverter

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

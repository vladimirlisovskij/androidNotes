package com.example.notes.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.dataBase.entity.Employee

@Database(entities = [Employee::class], version = 1)
abstract class AbstractDB: RoomDatabase() {
    abstract fun employeeDAO(): EmployeeDao

    companion object {
        private const val DB_NAME: String = "notesDataBase"

        private var instance: AbstractDB? = null

        @JvmStatic
        fun getDB(context: Context): AbstractDB {
            instance = Room.databaseBuilder(context.applicationContext, AbstractDB::class.java, DB_NAME)
                .build()
            return instance!!
        }
    }
}
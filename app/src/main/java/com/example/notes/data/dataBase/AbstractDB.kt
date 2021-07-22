package com.example.notes.data.dataBase

import android.content.Context
import androidx.room.*
import com.example.notes.data.dataBase.entity.Employee
import com.example.notes.data.dataBase.entity.ListStringConverter

@Database(entities = [Employee::class], version = 1)
@TypeConverters(ListStringConverter::class)
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
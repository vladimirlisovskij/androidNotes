package com.example.notes.data.dataBase

import android.content.Context
import androidx.room.*
import com.example.notes.data.dataBase.dao.EmployeeDao
import com.example.notes.data.dataBase.dao.WidgetNoteDao
import com.example.notes.data.dataBase.entitie.NoteEmployee
import com.example.notes.data.dataBase.entitie.WidgetEmployee
import com.google.gson.Gson
import javax.inject.Inject

class ListStringConverter {
    @TypeConverter
    fun fromString(value: String): List<String> =
        Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun fromList(value: List<String>): String = Gson().toJson(value)
}

@Database(entities = [WidgetEmployee::class, NoteEmployee::class], version = 1)
@TypeConverters(ListStringConverter::class)
abstract class AbstractDB : RoomDatabase() {
    companion object {
        private const val DB_NAME: String = "notesDataBase"

        var database: AbstractDB? = null

        fun createInstance(context: Context): AbstractDB {
            if (database == null) database = Room.databaseBuilder(
                context,
                AbstractDB::class.java,
                DB_NAME
            )
                .build()
            return database!!
        }
    }

    abstract fun employeeDAO(): EmployeeDao

    abstract fun widgetNoteDao(): WidgetNoteDao
}

class DaoProvider @Inject constructor(
    context: Context
) {
    private val db = AbstractDB.createInstance(context)

    val widgetDAO = db.widgetNoteDao()
}

package com.example.notes.cleanArchitecture.domain.repository

import com.example.notes.cleanArchitecture.domain.dataSource.WidgetNoteDataSource
import com.example.notes.cleanArchitecture.domain.enitites.WidgetNoteEntity
import javax.inject.Inject

class WidgetNoteRepository @Inject constructor(
    private val dataSource: WidgetNoteDataSource
) {
    fun getWidgetNotesByIDs(widgetIDs: List<Long>) = dataSource.getWidgetNotesByID(widgetIDs)

    fun insertWidgetNote(note: WidgetNoteEntity) = dataSource.insertWidgetNote(note)

    fun deleteWidgetNoteByIDs(widgetIDs: List<Long>) = dataSource.deleteWidgetNoteByID(widgetIDs)
}
package com.example.notes.domain.repository

import com.example.notes.domain.dataSource.WidgetNoteDataSource
import com.example.notes.domain.enitites.WidgetNoteEntity
import javax.inject.Inject

class WidgetNoteRepository @Inject constructor(
    private val dataSource: WidgetNoteDataSource
) {
    fun getWidgetNotesByIDs(widgetIDs: List<Long>) = dataSource.getWidgetNotesByID(widgetIDs)

    fun insertWidgetNote(note: WidgetNoteEntity) = dataSource.insertWidgetNote(note)

    fun deleteWidgetNoteByIDs(widgetIDs: List<Long>) = dataSource.deleteWidgetNoteByID(widgetIDs)
}
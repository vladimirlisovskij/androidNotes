package com.example.notes.cleanArchitecture.data.dataSource

import com.example.notes.cleanArchitecture.data.dataBase.dao.WidgetNoteDao
import com.example.notes.cleanArchitecture.data.dataBase.entitie.toData
import com.example.notes.cleanArchitecture.data.dataBase.entitie.toDomain
import com.example.notes.cleanArchitecture.domain.dataSource.WidgetNoteDataSource
import com.example.notes.cleanArchitecture.domain.enitites.WidgetNoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WidgetNoteDataSourceImpl @Inject constructor(
    private val widgetNoteDao: WidgetNoteDao
): WidgetNoteDataSource {
    override fun getWidgetNotesByID(widgetIDs: List<Long>): Single<HashMap<Long, WidgetNoteEntity>> {
        return Single.fromCallable {
            val res = HashMap<Long, WidgetNoteEntity>()
            widgetIDs.forEach { id ->
                widgetNoteDao.getById(id)?.let { note ->
                    res[id] = note.toDomain()
                }
            }
            res
        }
    }

    override fun insertWidgetNote(note: WidgetNoteEntity): Completable {
        return Completable.fromCallable {
            widgetNoteDao.insert(note.toData())
        }
    }

    override fun deleteWidgetNoteByID(widgetIDs: List<Long>): Completable {
        return Completable.fromCallable {
            widgetIDs.forEach(widgetNoteDao::deleteById)
        }
    }
}
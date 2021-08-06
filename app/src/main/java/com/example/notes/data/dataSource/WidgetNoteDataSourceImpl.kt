package com.example.notes.data.dataSource

import com.example.notes.data.dataBase.DaoProvider
import com.example.notes.data.dataBase.entitie.toData
import com.example.notes.data.dataBase.entitie.toDomain
import com.example.notes.domain.dataSource.WidgetNoteDataSource
import com.example.notes.domain.enitites.WidgetNoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WidgetNoteDataSourceImpl @Inject constructor(
    private val daoProvider: DaoProvider
): WidgetNoteDataSource {
    override fun getWidgetNotesByID(widgetIDs: List<Long>): Single<HashMap<Long, WidgetNoteEntity>> {
        return Single.fromCallable {
            val res = HashMap<Long, WidgetNoteEntity>()
            widgetIDs.forEach { id ->
                daoProvider.widgetDAO.getById(id)?.let { note ->
                    res[id] = note.toDomain()
                }
            }
            res
        }
    }

    override fun insertWidgetNote(note: WidgetNoteEntity): Completable {
        return Completable.fromCallable {
            daoProvider.widgetDAO.insert(note.toData())
        }
    }

    override fun deleteWidgetNoteByID(widgetIDs: List<Long>): Completable {
        return Completable.fromCallable {
            widgetIDs.forEach(daoProvider.widgetDAO::deleteById)
        }
    }
}
package com.example.notes.data.dataSource

import com.example.notes.dataBase.EmployeeDao
import com.example.notes.dataBase.entity.toData
import com.example.notes.dataBase.entity.toDomain
import com.example.notes.domain.dataSource.DataSource
import com.example.notes.domain.enitites.NoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class DataSourceImpl: DataSource {
    @Inject lateinit var employeeDao: EmployeeDao

    override fun addNote(noteEntity: NoteEntity): Completable {
        return employeeDao.insert(noteEntity.toData())
    }

    override fun delete(noteID: Int): Completable {
        return employeeDao.deleteById(noteID)
    }

    override fun getNotes(): Single<List<NoteEntity>> {
        return employeeDao.getAll().map { employeeList ->
            employeeList.map { it.toDomain() }
        }
    }
}
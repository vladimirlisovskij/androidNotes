package com.example.notes.data.dataSource

import com.example.notes.data.dataBase.EmployeeDao
import com.example.notes.data.dataBase.entity.toData
import com.example.notes.data.dataBase.entity.toDomain
import com.example.notes.domain.dataSource.DataSource
import com.example.notes.domain.enitites.NoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class DataSourceImpl @Inject constructor(
    private val employeeDao: EmployeeDao
) : DataSource {
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
package com.example.notes.cleanArchitecture.data.dataSource

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.notes.application.MainApplication
import com.example.notes.cleanArchitecture.data.dataBase.EmployeeDao
import com.example.notes.cleanArchitecture.data.dataBase.toData
import com.example.notes.cleanArchitecture.data.dataBase.toDomain
import com.example.notes.cleanArchitecture.domain.dataSource.DataSource
import com.example.notes.cleanArchitecture.domain.enitites.NoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val employeeDao: EmployeeDao
) : DataSource {
    private companion object {
        const val IMAGE_DIR = "imageDir"
    }

    private val directory = MainApplication.instance.applicationContext.getDir(IMAGE_DIR, Context.MODE_PRIVATE)

    override fun addNote(noteEntity: NoteEntity): Single<Long> {
        return employeeDao.insert(noteEntity.toData())
    }

    override fun deleteImageById(noteID: List<Long>): Completable {
        return Completable.fromAction {
            noteID.forEach {
                employeeDao.getById(it)?.image?.forEach { name ->
                    with(File(directory, name)) {
                        if(exists()) delete()
                    }
                }
            }
        }
    }

    override fun deleteNote(noteID: List<Long>): Completable {
        return employeeDao.deleteByListId(noteID)
    }

    override fun getNotes(): Single<List<NoteEntity>> {
        return employeeDao.getAll().map { employeeList ->
            employeeList.map { it.toDomain() }
        }
    }

    override fun multiLoadImage(key: List<String>): Single<List<Bitmap>> {
        return Single.fromCallable{
            key.map {
                BitmapFactory.decodeStream(FileInputStream(File(directory, it)))
            }
        }
    }

    override fun multiSaveImage(keys: List<Bitmap>): Single<List<String>> {
        return Single.fromCallable {
            keys.map {
                val key = Date().time.toString()
                val fos = FileOutputStream(File(directory, key))
                try {
                    it.compress(Bitmap.CompressFormat.PNG, 100, fos)
                } catch (e: Exception) {
                    fos.close()
                }
                return@map key
            }
        }
    }
}
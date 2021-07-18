package com.example.notes.data.dataSource

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.notes.application.MainApplication
import com.example.notes.data.dataBase.EmployeeDao
import com.example.notes.data.dataBase.entity.toData
import com.example.notes.data.dataBase.entity.toDomain
import com.example.notes.domain.dataSource.DataSource
import com.example.notes.domain.enitites.NoteEntity
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
                employeeDao.getById(it)?.image?.let {
                    with(File(directory, it)) {
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

    override fun saveImage(bitmap: Bitmap?): Single<String> {
        return Single.fromCallable {
            bitmap ?: return@fromCallable ""
            val key = Date().time.toString()
            val fos = FileOutputStream(File(directory, key))
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            } catch (e: Exception) {
                fos.close()
            }
            return@fromCallable key
        }
    }

    override fun loadImage(key: String): Single<Bitmap> {
        return Single.fromCallable {
            BitmapFactory.decodeStream(FileInputStream(File(directory, key)))
        }
    }
}
package com.example.notes.cleanArchitecture.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notes.cleanArchitecture.data.dataBase.entitie.WidgetEmployee
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM widgetemployee")
    fun getAll(): Single<List<WidgetEmployee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: WidgetEmployee): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSync(note: WidgetEmployee)

    @Query("delete from widgetemployee where id in (:idList)")
    fun deleteByListId(idList: List<Long>): Completable

    @Query("select * from widgetemployee where id=:id")
    fun getById(id: Long): WidgetEmployee?

    @Query("select * from widgetemployee where id=:id")
    fun getById2(id: Long): Maybe<WidgetEmployee>
}


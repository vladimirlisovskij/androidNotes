package com.example.notes.cleanArchitecture.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notes.cleanArchitecture.data.dataBase.entitie.Employee
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee")
    fun getAll(): Single<List<Employee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Employee): Single<Long>

    @Query("delete from employee where id in (:idList)")
    fun deleteByListId(idList: List<Long>): Completable

    @Query("select * from employee where id=:id")
    fun getById(id: Long): Employee?
}


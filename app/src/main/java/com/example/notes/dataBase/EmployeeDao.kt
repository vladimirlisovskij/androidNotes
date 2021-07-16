package com.example.notes.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notes.dataBase.entity.Employee
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee")
    fun getAll(): Single<List<Employee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Employee): Completable

    @Query("delete from employee where id=:id")
    fun deleteById(id: Int): Completable
}
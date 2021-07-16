package com.example.notes.data.di

import com.example.notes.application.MainApplication
import com.example.notes.dataBase.AbstractDB
import com.example.notes.dataBase.EmployeeDao
import dagger.Module
import dagger.Provides

@Module
class DBModule {
    @Provides
    fun provideDB(): EmployeeDao = AbstractDB
        .getDB(MainApplication.instance)
        .employeeDAO()
}
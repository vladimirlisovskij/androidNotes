package com.example.notes.data.di

import com.example.notes.application.MainApplication
import com.example.notes.data.dataBase.AbstractDB
import com.example.notes.data.dataBase.EmployeeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {
    @Provides
    fun provideDB(): EmployeeDao = AbstractDB
        .getDB(MainApplication.instance)
        .employeeDAO()
}
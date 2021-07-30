package com.example.notes.data.di

import com.example.notes.data.dataBase.AbstractDB
import dagger.Module
import dagger.Provides

@Module
class DBModule {
    @Provides
    fun provideDao() = AbstractDB.instance.employeeDAO()
}
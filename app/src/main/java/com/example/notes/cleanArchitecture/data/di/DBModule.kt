package com.example.notes.cleanArchitecture.data.di

import com.example.notes.cleanArchitecture.data.dataBase.AbstractDB
import dagger.Module
import dagger.Provides

@Module
class DBModule {
    @Provides
    fun provideDao() = AbstractDB.instance.employeeDAO()

    @Provides
    fun provideWidgetDao() = AbstractDB.instance.widgetNoteDao()
}
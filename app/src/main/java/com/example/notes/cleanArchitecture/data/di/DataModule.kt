package com.example.notes.cleanArchitecture.data.di

import com.example.notes.cleanArchitecture.data.dataSource.NoteDataSourceImpl
import com.example.notes.cleanArchitecture.data.dataSource.WidgetNoteDataSourceImpl
import com.example.notes.cleanArchitecture.domain.dataSource.NoteDataSource
import com.example.notes.cleanArchitecture.domain.dataSource.WidgetNoteDataSource
import dagger.Binds
import dagger.Module

@Module(includes = [DBModule::class])
abstract class DataModule {
    @Binds
    abstract fun bindNoteDataSource(noteDataSourceImpl: NoteDataSourceImpl): NoteDataSource

    @Binds
    abstract fun bindWidgetNoteDataSource(widgetNoteDataSourceImpl: WidgetNoteDataSourceImpl): WidgetNoteDataSource
}



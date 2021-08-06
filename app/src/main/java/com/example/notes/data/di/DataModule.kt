package com.example.notes.data.di

import com.example.notes.data.dataSource.AuthDataSourceImpl
import com.example.notes.data.dataSource.NoteDataSourceImpl
import com.example.notes.data.dataSource.WidgetNoteDataSourceImpl
import com.example.notes.domain.dataSource.AuthDataSource
import com.example.notes.domain.dataSource.NoteDataSource
import com.example.notes.domain.dataSource.WidgetNoteDataSource
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {
    @Binds
    abstract fun bindNoteDataSource(noteDataSourceImpl: NoteDataSourceImpl): NoteDataSource

    @Binds
    abstract fun bindWidgetNoteDataSource(widgetNoteDataSourceImpl: WidgetNoteDataSourceImpl): WidgetNoteDataSource

    @Binds
    abstract fun authDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource
}



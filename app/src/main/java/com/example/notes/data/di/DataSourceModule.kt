package com.example.notes.data.di

import com.example.notes.application.MainApplication
import com.example.notes.data.dataSource.DataSourceImpl
import com.example.notes.domain.dataSource.DataSource
import com.example.notes.domain.repository.Repository
import com.example.notes.domain.useCases.AddNoteUseCase
import com.example.notes.domain.useCases.GetNotesUseCase
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {
    @Provides
    fun provideDataSource(): DataSource = DataSourceImpl().apply {
        MainApplication.instance.dataInjector.inject(this)
    }
}
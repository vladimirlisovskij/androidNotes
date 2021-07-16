package com.example.notes.data.di

import com.example.notes.application.MainApplication
import com.example.notes.data.dataBase.AbstractDB
import com.example.notes.data.dataSource.DataSourceImpl
import com.example.notes.domain.dataSource.DataSource
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    private val dataSourceImpl = DataSourceImpl(
        AbstractDB
        .getDB(MainApplication.instance)
        .employeeDAO()
    )

    @Provides
    fun provideDataSource(): DataSource = dataSourceImpl
}
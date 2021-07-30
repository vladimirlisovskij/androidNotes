package com.example.notes.data.di

import com.example.notes.data.dataSource.DataSourceImpl
import com.example.notes.domain.dataSource.DataSource
import dagger.Binds
import dagger.Module

@Module(includes = [DBModule::class])
abstract class DataModule {
    @Binds
    abstract fun bindDataSource(dataSourceImpl: DataSourceImpl): DataSource
}



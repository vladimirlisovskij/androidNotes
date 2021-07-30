package com.example.notes.cleanArchitecture.data.di

import com.example.notes.cleanArchitecture.data.dataSource.DataSourceImpl
import com.example.notes.cleanArchitecture.domain.dataSource.DataSource
import dagger.Binds
import dagger.Module

@Module(includes = [DBModule::class])
abstract class DataModule {
    @Binds
    abstract fun bindDataSource(dataSourceImpl: DataSourceImpl): DataSource
}



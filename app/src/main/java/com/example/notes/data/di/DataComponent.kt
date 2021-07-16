package com.example.notes.data.di

import com.example.notes.data.dataSource.DataSourceImpl
import com.example.notes.domain.repository.Repository
import dagger.Component

@Component(modules = [DBModule::class])
interface DataComponent {
    fun getDataSource(): DataSourceImpl
}
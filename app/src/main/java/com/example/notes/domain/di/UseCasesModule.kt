package com.example.notes.domain.di

import com.example.notes.application.MainApplication
import com.example.notes.domain.repository.Repository
import com.example.notes.domain.useCases.AddNoteUseCase
import com.example.notes.domain.useCases.DelNoteUseCase
import com.example.notes.domain.useCases.GetNotesUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    private val repository = Repository().apply {
        MainApplication.instance.dataInjector.inject(this)
    }

    @Provides
    fun provideAddNoteUseCase() = AddNoteUseCase(repository)

    @Provides
    fun provideGetNotesIDUseCase() = GetNotesUseCase(repository)

    @Provides
    fun provideDelNoteIDUseCase() = DelNoteUseCase(repository)
}
package com.example.notes.presenter.di

import com.example.notes.presenter.coordinator.Coordinator
import com.github.terrakok.cicerone.Cicerone
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    private val cicerone = Cicerone.create()
    private val coordinator = Coordinator(cicerone.router)

    @Provides
    fun provideHolder() = cicerone.getNavigatorHolder()

    @Provides
    fun provideCoordinator() = coordinator
}
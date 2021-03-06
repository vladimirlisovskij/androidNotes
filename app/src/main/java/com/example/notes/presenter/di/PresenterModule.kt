package com.example.notes.presenter.di

import com.example.notes.presenter.backCoordinator.OnBackListener
import com.github.terrakok.cicerone.Cicerone
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
    private val cicerone = Cicerone.create()
    private val onBackFabric = OnBackListener.create()

    @Provides
    @Singleton
    fun provideHolder() = cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideRouter() = cicerone.router

    @Provides
    @Singleton
    fun provideEmitter() = onBackFabric.emitter

    @Provides
    @Singleton
    fun provideCollector() = onBackFabric.collector
}


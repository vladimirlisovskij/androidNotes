package com.example.notes.presenter.di

import com.example.notes.presenter.coordinator.FragmentResultConstructor
import com.example.notes.presenter.coordinator.OnBackFabric
import com.github.terrakok.cicerone.Cicerone
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
    private val cicerone = Cicerone.create()
    private val onBackFabric = OnBackFabric()
    private val fragmentResultConstructor = FragmentResultConstructor()

    private val router = cicerone.router
    private  val holder = cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideHolder() = holder

    @Provides
    @Singleton
    fun provideRouter() = router

    @Provides
    @Singleton
    fun provideEmitter() = onBackFabric.emitter

    @Provides
    @Singleton
    fun provideCollector() = onBackFabric.collector

    @Provides
    @Singleton
    fun provideFragmentResultEmitter() = fragmentResultConstructor.fragmentResultEmitter

    @Provides
    @Singleton
    fun provideFragmentResultCollector() = fragmentResultConstructor.fragmentResultCollector
}
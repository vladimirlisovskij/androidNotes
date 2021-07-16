package com.example.notes.presenter.di

import com.github.terrakok.cicerone.Cicerone
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {
    private val cicerone = Cicerone.create()

    @Provides
    fun provideRouter() = cicerone.router

    @Provides
    fun provideHolder() = cicerone.getNavigatorHolder()
}
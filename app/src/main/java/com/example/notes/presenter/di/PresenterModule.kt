package com.example.notes.presenter.di

import android.util.Log
import com.example.notes.presenter.coordinator.Coordinator
import com.github.terrakok.cicerone.Cicerone
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
    private val cicerone = Cicerone.create().apply {
        Log.d("tag", "created")
    }

    @Provides
    @Singleton
    fun provideHolder() = cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideRouter() = cicerone.router
}
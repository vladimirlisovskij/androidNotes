package com.example.notes.presenter.di

import android.util.Log
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.coordinator.OnBackCollector
import com.example.notes.presenter.coordinator.OnBackListener
import com.example.notes.presenter.coordinator.Signal
import com.github.terrakok.cicerone.Cicerone
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Singleton

@Module
class PresenterModule {
    private val cicerone = Cicerone.create()

    private val activitySubject: PublishSubject<Signal> = PublishSubject.create()
    private val coordinatorSubject: PublishSubject<String> = PublishSubject.create()
    private val codeSubject: PublishSubject<String> = PublishSubject.create()

    private val onBackListener = OnBackListener(coordinatorSubject, activitySubject, codeSubject)
    private val onBackCollector = OnBackCollector(activitySubject, coordinatorSubject, codeSubject)

    @Provides
    @Singleton
    fun provideHolder() = cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideRouter() = cicerone.router

    @Provides
    @Singleton
    fun provideListener() = onBackListener

    @Provides
    @Singleton
    fun provideCollector() = onBackCollector
}
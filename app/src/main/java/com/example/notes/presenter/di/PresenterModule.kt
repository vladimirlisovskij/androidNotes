package com.example.notes.presenter.di

import com.example.notes.presenter.coordinator.Coordinator
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

    private val coordinatorSubject: PublishSubject<Signal> = PublishSubject.create()

    private val onBackListener = OnBackListener(coordinatorSubject)

    @Provides
    @Singleton
    fun provideHolder() = cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideCoordinator() = Coordinator(cicerone.router, coordinatorSubject)

    @Provides
    @Singleton
    fun provideListener() = onBackListener
}
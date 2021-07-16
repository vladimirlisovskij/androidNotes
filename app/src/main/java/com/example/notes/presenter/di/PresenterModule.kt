package com.example.notes.presenter.di

import com.example.notes.application.MainApplication
import com.example.notes.domain.di.UseCasesModule
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.mainActivity.MainViewModel
import com.example.notes.presenter.mainActivity.MainViewModelImpl
import com.example.notes.presenter.noteEdit.NoteViewModel
import com.example.notes.presenter.noteEdit.NoteViewModelImpl
import com.example.notes.presenter.recycler.RecyclerViewModel
import com.example.notes.presenter.recycler.RecyclerViewModelImpl
import com.example.notes.view.noteEdit.NoteEditView
import com.github.terrakok.cicerone.Navigator
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    fun provideRecyclerVM(): RecyclerViewModel = RecyclerViewModelImpl().apply {
        MainApplication.instance.presenterInjector.inject(this)
    }

    @Provides
    fun provideMainVM(): MainViewModel = MainViewModelImpl().apply {
        MainApplication.instance.presenterInjector.inject(this)
    }

    @Provides
    fun provideNoteEditVM(): NoteViewModel = NoteViewModelImpl().apply {
        MainApplication.instance.presenterInjector.inject(this)
    }

    @Provides
    fun provideCoordinator(): Coordinator = Coordinator().apply {
        MainApplication.instance.presenterInjector.inject(this)
    }
}
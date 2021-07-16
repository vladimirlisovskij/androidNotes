package com.example.notes.presenter.di

import com.example.notes.application.MainApplication
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.mainActivity.MainViewModel
import com.example.notes.presenter.mainActivity.MainViewModelImpl
import com.example.notes.presenter.noteEdit.NoteViewModel
import com.example.notes.presenter.noteEdit.NoteViewModelImpl
import com.example.notes.presenter.recycler.RecyclerViewModel
import com.example.notes.presenter.recycler.RecyclerViewModelImpl
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
    fun provideRecyclerVM(): RecyclerViewModel = RecyclerViewModelImpl(
        MainApplication.instance.useCaseComponent.getGetNotesCase(),
        coordinator
    )

    @Provides
    fun provideMainVM(): MainViewModel = MainViewModelImpl(
        coordinator
    )

    @Provides
    fun provideNoteEditVM(): NoteViewModel = NoteViewModelImpl(
        MainApplication.instance.useCaseComponent.getAddNoteCase(),
        MainApplication.instance.useCaseComponent.getDelNoteCase(),
        coordinator
    )
}
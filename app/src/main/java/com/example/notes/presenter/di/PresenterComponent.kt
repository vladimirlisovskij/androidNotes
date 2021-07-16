package com.example.notes.presenter.di

import com.example.notes.domain.di.UseCasesModule
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.mainActivity.MainViewModelImpl
import com.example.notes.presenter.noteEdit.NoteViewModelImpl
import com.example.notes.presenter.recycler.RecyclerViewModelImpl
import com.example.notes.view.mainActivity.MainActivity
import com.example.notes.view.noteEdit.NoteEditView
import com.example.notes.view.recycler.ListNotesView
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component(modules = [UseCasesModule::class, PresenterModule::class, NavigationModule::class])
interface PresenterComponent{
    fun inject(target: MainActivity)

    fun inject(target: ListNotesView)
    fun inject(target: NoteEditView)

    fun inject(target: MainViewModelImpl)
    fun inject(target: NoteViewModelImpl)
    fun inject(target: RecyclerViewModelImpl)

    fun inject(target: Coordinator)
}
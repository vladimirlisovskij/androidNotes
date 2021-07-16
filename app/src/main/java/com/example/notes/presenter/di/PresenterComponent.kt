package com.example.notes.presenter.di

import com.example.notes.presenter.mainActivity.MainViewModel
import com.example.notes.presenter.noteEdit.NoteViewModel
import com.example.notes.presenter.recycler.RecyclerViewModel
import com.example.notes.view.mainActivity.MainActivity
import com.example.notes.view.noteEdit.NoteEditView
import com.example.notes.view.recycler.ListNotesView
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PresenterModule::class])
interface PresenterComponent {
    fun inject(target: MainActivity)
    fun inject(target: ListNotesView)
    fun inject(target: NoteEditView)

    fun getListNotesView(): ListNotesView
    fun getNoteEditView(): NoteEditView

    fun getListNotesVM(): RecyclerViewModel
    fun getNoteEditVM(): NoteViewModel
    fun getMainVM(): MainViewModel
}
package com.example.notes.di

import com.example.notes.data.di.DataModule
import com.example.notes.presenter.di.PresenterModule
import com.example.notes.presenter.noteEdit.NoteViewModel
import com.example.notes.presenter.recycler.RecyclerViewModel
import com.example.notes.view.mainActivity.MainActivity
import com.example.notes.view.noteEdit.NoteEditView
import com.example.notes.view.recycler.ListNotesView
import dagger.Component

@Component(modules = [PresenterModule::class, DataModule::class])
interface MainComponent {
    fun inject(target: MainActivity)
    fun inject(target: ListNotesView)
    fun inject(target: NoteEditView)
}


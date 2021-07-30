package com.example.notes.di

import com.example.notes.cleanArchitecture.data.di.DataModule
import com.example.notes.cleanArchitecture.presenter.di.PresenterModule
import com.example.notes.cleanArchitecture.view.editor.EditorView
import com.example.notes.cleanArchitecture.view.gallery.GalleryView
import com.example.notes.cleanArchitecture.view.mainActivity.MainActivity
import com.example.notes.cleanArchitecture.view.noteEdit.NoteEditView
import com.example.notes.cleanArchitecture.view.recycler.ListNotesView
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PresenterModule::class, DataModule::class])
interface MainComponent {
    fun inject(target: MainActivity)
    fun inject(target: ListNotesView)
    fun inject(target: NoteEditView)
    fun inject(target: GalleryView)
    fun inject(target: EditorView)
}


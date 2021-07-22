package com.example.notes.di

import com.example.notes.data.di.DataModule
import com.example.notes.presenter.di.PresenterModule
import com.example.notes.view.gallery.GalleryView
import com.example.notes.view.mainActivity.MainActivity
import com.example.notes.view.noteEdit.NoteEditView
import com.example.notes.view.recycler.ListNotesView
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PresenterModule::class, DataModule::class])
interface MainComponent {
    fun inject(target: MainActivity)
    fun inject(target: ListNotesView)
    fun inject(target: NoteEditView)
    fun inject(target: GalleryView)
}


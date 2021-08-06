package com.example.notes.di

import com.example.notes.data.di.DataModule
import com.example.notes.domain.useCases.DeleteWidgetNotesByIDsUseCase
import com.example.notes.domain.useCases.GetNotesByWidgetIdUseCase
import com.example.notes.presenter.di.ActivityModule
import com.example.notes.presenter.di.PresenterModule
import com.example.notes.view.editor.EditorView
import com.example.notes.view.gallery.GalleryView
import com.example.notes.view.login.LoginView
import com.example.notes.view.mainActivity.MainActivity
import com.example.notes.view.noteEdit.NoteEditView
import com.example.notes.view.recycler.ListNotesView
import com.example.notes.view.signin.SignInView
import com.example.notes.view.widgetConfigActivity.WidgetConfigActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PresenterModule::class, DataModule::class, ActivityModule::class])
interface MainComponent {
    fun inject(target: MainActivity)
    fun inject(target: WidgetConfigActivity)

    fun inject(target: ListNotesView)
    fun inject(target: NoteEditView)
    fun inject(target: GalleryView)
    fun inject(target: EditorView)
    fun inject(target: LoginView)
    fun inject(target: SignInView)

    fun getNotesByWidgetIdUseCase(): GetNotesByWidgetIdUseCase
    fun deleteWidgetUseCase(): DeleteWidgetNotesByIDsUseCase
}
package com.example.notes.presenter.coordinator

import com.example.notes.view.entities.NoteRecyclerHolder
import com.example.notes.view.noteEdit.NoteEditView
import com.example.notes.view.recycler.ListNotesView
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class Coordinator(
    private val router: Router
){

    fun openListNote() {
        router.newRootScreen(FragmentScreen{ ListNotesView.newInstance()})
    }

    fun openNoteEditor(noteRecyclerHolder: NoteRecyclerHolder) {
        router.navigateTo(FragmentScreen{ NoteEditView.newInstance(noteRecyclerHolder)})
    }
}
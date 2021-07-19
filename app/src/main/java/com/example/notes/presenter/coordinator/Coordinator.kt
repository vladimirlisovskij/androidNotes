package com.example.notes.presenter.coordinator

import android.graphics.Bitmap
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.view.noteEdit.NoteEditView
import com.example.notes.view.recycler.ListNotesView
import com.example.notes.view.viewer.ViewerView
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class Coordinator @Inject constructor(
    private val router: Router,
){
    fun back() {
        router.exit()
    }

    fun openListNote() {
        router.newRootScreen(FragmentScreen{ ListNotesView.newInstance()})
    }

    fun openNoteEditor(noteRecyclerHolder: NoteRecyclerHolder) {
        router.navigateTo(FragmentScreen{ NoteEditView.newInstance(noteRecyclerHolder)})
    }

    fun openViewer(bitmap: Bitmap) {
        router.navigateTo(FragmentScreen{ ViewerView.newInstance(bitmap) })
    }
}
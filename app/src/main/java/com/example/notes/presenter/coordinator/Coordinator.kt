package com.example.notes.presenter.coordinator

import android.os.Parcelable
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.presenter.gallery.GalleryViewModel
import com.example.notes.view.gallery.GalleryView
import com.example.notes.view.noteEdit.NoteEditView
import com.example.notes.view.recycler.ListNotesView
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class Coordinator @Inject constructor(
    private val router: Router,
    private val fragmentResultCollector: FragmentResultCollector
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

    fun startGallery(refs: List<String>): Observable<Parcelable> {
        router.navigateTo(FragmentScreen{ GalleryView.newInstance(refs)})
        return fragmentResultCollector.result
    }
}
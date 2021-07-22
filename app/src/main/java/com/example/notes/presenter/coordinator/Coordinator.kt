package com.example.notes.presenter.coordinator

import android.os.Parcelable
import com.example.notes.base.ResultFragment
import com.example.notes.base.ResultViewModel
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.view.gallery.GalleryView
import com.example.notes.view.noteEdit.NoteEditView
import com.example.notes.view.recycler.ListNotesView
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class Coordinator @Inject constructor(
    private val router: Router
){
    fun back() {
        router.exit()
    }

    fun openListNote() {
        router.newRootScreen(FragmentScreen{ ListNotesView.newInstance()})
    }

    fun <T: ResultViewModel> startForResult(fragment: ResultFragment<T>): Observable<Parcelable> {
        val publishSubject = PublishSubject.create<Parcelable>()
        fragment.resultEmitter = publishSubject
        router.navigateTo(FragmentScreen{ fragment })
        return publishSubject
    }
}


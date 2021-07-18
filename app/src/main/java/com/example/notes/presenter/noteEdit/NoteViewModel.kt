package com.example.notes.presenter.noteEdit

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.base.BaseViewModel
import com.example.notes.domain.useCases.AddNoteUseCase
import com.example.notes.domain.useCases.DelNoteUseCase
import com.example.notes.domain.useCases.LoadImageUseCase
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.presenter.entities.toDomain
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

const val NOTEEDIT_BACK_CODE = "NOTEEDIT_BACK_CODE"

class NoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val delNoteUseCase: DelNoteUseCase,
    private val fileUseCase: LoadImageUseCase,
    private val coordinator: Coordinator
): BaseViewModel()  {
    private val mutableImageIntent = MutableLiveData<Intent>()
    val imageIntent: LiveData<Intent> = mutableImageIntent

    private val mutableImageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri> = mutableImageUri

    private val mutableImageBitmap = MutableLiveData<Bitmap?>()
    val imageBitmap: LiveData<Bitmap?> = mutableImageBitmap

    private val mutableOpenImage = MutableLiveData<Boolean>()
    val openImage: LiveData<Boolean> = mutableOpenImage

    private var isOpenImage = false

    init {
        coordinator.collector.code = NOTEEDIT_BACK_CODE

        disposable += coordinator.collector.getSubject().subscribe {
            if (it == NOTEEDIT_BACK_CODE) {
                if (isOpenImage) {
                    isOpenImage = false
                    mutableOpenImage.postValue(false)
                }
                else coordinator.collector.emit()
            }
        }
    }

    fun onApplyClick(noteRecyclerHolder: NoteRecyclerHolder, newImage: Bitmap?) {
        disposable += addNoteUseCase(noteRecyclerHolder.toDomain(), newImage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        coordinator.openListNote()
                        Log.d("tag", "insert OK")
                    },
                    {
                        coordinator.openListNote()
                        Log.d("tag", "error on apply get $it.toString()")
                    }
                )
    }

    fun onDelClick(id: Long) {
        disposable += delNoteUseCase(listOf(id))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    coordinator.openListNote()
                    Log.d("tag", "del OK")
                },
                {
                    coordinator.openListNote()
                    Log.d("tag", "error on del $it.toString()")
                }
            )
    }

    fun onOpenImage(){
        isOpenImage = true
        mutableOpenImage.postValue(true)
    }

    fun onCancelClick() {
        coordinator.openListNote()
    }

    fun onImageClick() {
        mutableImageIntent.postValue(Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
    }

    fun onActivityResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let {
                mutableImageUri.postValue(it)
            }
        }
    }

    fun loadImage(key: String) {
        disposable += fileUseCase(key)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe (
                { bitmap ->
                    mutableImageBitmap.postValue(bitmap)
                },
                {
                    mutableImageBitmap.postValue(null)
                    Log.d("tag", "error on load $it.toString()")
                }
            )
    }
 }
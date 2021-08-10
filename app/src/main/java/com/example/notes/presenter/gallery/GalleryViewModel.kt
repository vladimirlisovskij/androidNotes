package com.example.notes.presenter.gallery

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.presenter.backCoordinator.OnBackCollector
import com.example.notes.presenter.base.baseFragment.BaseViewModel
import com.example.notes.presenter.coordinator.Coordinator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val onBackCollector: OnBackCollector,
    private val coordinator: Coordinator,
) : BaseViewModel(onBackCollector, coordinator) {
    private val _selectionMode = MutableLiveData<Boolean>()
    val selectionMode get() = _selectionMode as LiveData<Boolean>

    private val _openImage = MutableLiveData<Bitmap?>()
    val openImage get() = _openImage as LiveData<Bitmap?>

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress get() = _showProgress as LiveData<Boolean>

    private val _bitmapList = MutableLiveData<List<Bitmap>>()
    val bitmapList get() = _bitmapList as LiveData<List<Bitmap>>

    private val _imageIntent = MutableLiveData<Intent>()
    val imageIntent get() = _imageIntent as LiveData<Intent>

    private val _imageUri = MutableLiveData<Uri>()
    val imageUri get() = _imageUri as LiveData<Uri>

    private val _onBack = MutableLiveData<Unit>()
    val onBack get() = _onBack as LiveData<Unit>

    private val _setResult = MutableLiveData<List<ByteArray>>()
    val setResult get() = _setResult as LiveData<List<ByteArray>>

    private var isSelected = false
    private var isOpenImage = false

    fun getBitmaps(refs: List<String>) {
        _showProgress.postValue(true)
        val listType = object : TypeToken<ByteArray>() {}.type
        _bitmapList.postValue(refs.map {
            val array: ByteArray = Gson().fromJson(it, listType)
            BitmapFactory.decodeByteArray(array, 0, array.size)
        })
        _showProgress.postValue(false)
    }

    fun onActivityResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let {
                _imageUri.postValue(it)
            }
        }
    }

    fun onImageAdd() {
        _imageIntent.postValue(
            Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        )
    }

    fun onApplyClick(bitmaps: List<Bitmap>) {
        _showProgress.postValue(true)
        _setResult.postValue(bitmaps.map {
            val stream = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.toByteArray()
        })
        _showProgress.postValue(false)
    }

    fun onExitClick() {
        when {
            isOpenImage -> {
                isOpenImage = false
                _openImage.postValue(null)
            }
            isSelected -> {
                isSelected = false
                _selectionMode.postValue(false)
            }
            else -> {
                _onBack.postValue(Unit)
            }
        }
    }

    fun onLongTab() {
        _selectionMode.postValue(true)
        isSelected = true
    }

    fun onDel() {
        _selectionMode.postValue(false)
        isSelected = false
    }

    fun onItemClick(image: Bitmap) {
        isOpenImage = true
        _openImage.postValue(image)
    }

    var isOpenState = false
        set(value) {
            if (field != value) {
                if (value) {
                    onBackCollector.subscribe {
                        onExitClick()
                    }
                    _selectionMode.postValue(false)
                } else {
                    onBackCollector.disposeLastSubscription()
                }
            }
            field = value
        }
}
package com.example.notes.presenter.gallery

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.base.BaseViewModel
import com.example.notes.domain.useCases.LoadImageUseCase
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val fileUseCase: LoadImageUseCase
): BaseViewModel() {
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

    private val _setResult = MutableLiveData<List<String>>()
    val setResult get() = _setResult as LiveData<List<String>>

    private val _galleryIsOpen = MutableLiveData<Boolean>()
    val galleryOpen get() = _galleryIsOpen as LiveData<Boolean>

    override fun onCreateView() {
        super.onCreateView()
        _galleryIsOpen.postValue(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _galleryIsOpen.postValue(false)
    }

    private var isSelected = false
    private var isOpenImage = false

    fun getBitmaps(refs: List<String>) {
        _showProgress.postValue(true)
        fileUseCase.multiLoadImage(refs).simpleSingleSubscribe {
            _bitmapList.postValue(it)
            _showProgress.postValue(false)
        }
    }

    fun onActivityResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let {
                _imageUri.postValue(it)
            }
        }
    }

    fun onImageAdd() {
        _imageIntent.postValue(Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
    }

    fun onApplyClick(bitmaps: List<Bitmap>) {
        _showProgress.postValue(true)
        fileUseCase.multiSaveImage(bitmaps).simpleSingleSubscribe {
            _setResult.postValue(it)
            _showProgress.postValue(false)
            _onBack.postValue(Unit)
        }
    }

    fun onBackClick() {
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
}
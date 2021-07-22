package com.example.notes.presenter.gallery

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.base.ResultViewModel
import com.example.notes.domain.useCases.LoadImageUseCase
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.coordinator.OnBackCollector
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val coordinator: Coordinator,
    private val onBackCollector: OnBackCollector,
    private val fileUseCase: LoadImageUseCase
): ResultViewModel() {
    @Parcelize
    data class GalleryResult(
        val links: List<String>
    ) : Parcelable

    private val mutableSelectedMode = MutableLiveData<Boolean>()
    val selectionMode: LiveData<Boolean> = mutableSelectedMode

    private val mutableOpenImage = MutableLiveData<Bitmap?>()
    val openImage: LiveData<Bitmap?> = mutableOpenImage

    private val mutableShowProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = mutableShowProgress

    private val mutableBitmapList = MutableLiveData<List<Bitmap>>()
    val bitmapList: LiveData<List<Bitmap>> = mutableBitmapList

    private val mutableImageIntent = MutableLiveData<Intent>()
    val imageIntent: LiveData<Intent> = mutableImageIntent

    private val mutableImageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri> = mutableImageUri

    private var isSelected = false
    private var isOpenImage = false

    override fun onCreate() {
        super.onCreate()
        onBackCollector.subscribe {
            onBackClick()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackCollector.disposeLastSubscription()
    }

    fun getBitmaps(refs: List<String>) {
        fileUseCase.multiLoadImage(refs).simpleSingleSubscribe {
            mutableBitmapList.postValue(it)
        }
    }

    fun onActivityResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let {
                mutableImageUri.postValue(it)
            }
        }
    }

    fun onImageAdd() {
        mutableImageIntent.postValue(Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
    }

    fun onApplyClick(bitmaps: List<Bitmap>) {
        mutableShowProgress.postValue(true)
        fileUseCase.multiSaveImage(bitmaps).simpleSingleSubscribe {
            mutableSetResult.postValue(GalleryResult(it))
            mutableShowProgress.postValue(false)
            coordinator.back()
        }
    }

    fun onBackClick() {
        when {
            isOpenImage -> {
                isOpenImage = false
                mutableOpenImage.postValue(null)
            }
            isSelected -> {
                isSelected = false
                mutableSelectedMode.postValue(false)
            }
            else -> {
                coordinator.back()
            }
        }
    }

    fun onLongTab() {
        mutableSelectedMode.postValue(true)
        isSelected = true
    }

    fun onDel() {
        mutableSelectedMode.postValue(false)
        isSelected = false
    }


    fun onItemClick(image: Bitmap) {
        isOpenImage = true
        mutableOpenImage.postValue(image)
    }
}
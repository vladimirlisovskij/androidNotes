package com.example.notes.view.viewer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.notes.R
import com.example.notes.base.BaseView
import com.example.notes.databinding.FragViewerBinding
import com.example.notes.di.Injector
import com.example.notes.presenter.viewer.ViewerViewModel
import java.io.ByteArrayOutputStream
import javax.inject.Inject

private const val ARG_IMAGE = "ARG_IMAGE"

class ViewerView: BaseView<ViewerViewModel>(R.layout.frag_viewer) {
    companion object {
        fun newInstance(bitmap: Bitmap) = ViewerView().apply {
            this.bitmap = bitmap
        }
    }

    @Inject override lateinit var viewModel: ViewerViewModel

    var bitmap: Bitmap? = null
    private lateinit var binding: FragViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragViewerBinding.bind(view)
        binding.imageView.setImageBitmap(bitmap)
    }
}
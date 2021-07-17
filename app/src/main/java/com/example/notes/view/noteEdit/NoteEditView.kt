package com.example.notes.view.noteEdit

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.LruCache
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.example.notes.R
import com.example.notes.base.BaseView
import com.example.notes.databinding.FragNoteEditBinding
import com.example.notes.di.Injector
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.presenter.noteEdit.NoteViewModel
import com.squareup.picasso.Picasso
import java.util.*
import javax.inject.Inject

private const val ARG_HOLDER = "ARG_HOLDER"

class NoteEditView: BaseView<NoteViewModel>(R.layout.frag_note_edit) {
    companion object {
        fun newInstance(noteRecyclerHolder: NoteRecyclerHolder) = NoteEditView().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_HOLDER, noteRecyclerHolder)
            }
        }
    }

    @Inject override lateinit var viewModel: NoteViewModel

    private var holder: NoteRecyclerHolder? = null
    private var noteID: Int = 0
    private lateinit var binding: FragNoteEditBinding
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        viewModel.onActivityResult(it)
    }

    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    private val lruCache = LruCache<String, Bitmap>(maxMemory)

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.component.inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            holder = it.getParcelable(ARG_HOLDER)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragNoteEditBinding.bind(view)

        with(binding) {
            btnApply.setOnClickListener {
                val key = Date().time.toString()
                val bitmap = (image.drawable as BitmapDrawable).bitmap
                lruCache.put(key, bitmap)

                viewModel.onApplyClick(

                    NoteRecyclerHolder(
                        id = noteID,
                        header = etHeader.text.toString(),
                        desc = etDesc.text.toString(),
                        body = etBody.text.toString(),
                        image = key
                ))
            }

            btnDelete.setOnClickListener {
                viewModel.onDelClick(noteID)
            }

            holder?.let {
                etHeader.setText(it.header)
                etDesc.setText(it.desc)
                etBody.setText(it.body)
                noteID = it.id
                image.setImageBitmap(lruCache.get(it.image))
            }

            image.setOnClickListener {
                viewModel.onImageClick()
            }

        }

        viewModel.imageUri.observe(viewLifecycleOwner) {
            Picasso
                .get()
                .load(it)
                .noPlaceholder()
                .centerCrop()
                .fit()
                .into(binding.image)
        }

        viewModel.imageIntent.observe(viewLifecycleOwner) {
            resultLauncher.launch(it)
        }
    }
}
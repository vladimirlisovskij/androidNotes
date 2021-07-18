package com.example.notes.view.noteEdit

import android.content.DialogInterface
import android.graphics.Bitmap
import android.opengl.Visibility
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.notes.R
import com.example.notes.base.BaseView
import com.example.notes.databinding.FragNoteEditBinding
import com.example.notes.di.Injector
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.presenter.noteEdit.NoteViewModel
import com.example.notes.presenter.recycler.NoteRecyclerAdapter
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
    private var curBitmap: Bitmap? = null
    private var noteID: Long = 0
    private lateinit var binding: FragNoteEditBinding
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        viewModel.onActivityResult(it)
    }
    private lateinit var oldKey: String

    private var dialog: AlertDialog? = null

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

        dialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage("Что вы хотите сделать?")
                setPositiveButton("открыть изображение"
                ) { _, _ ->
                    curBitmap?.let {
                        viewModel.onOpenImage()
                    }
                }
                setNeutralButton("изменить изображние"
                ) { _, _ ->
                    viewModel.onImageClick()
                }
            }
            builder.create()
        }

        setHasOptionsMenu(true)

        with(binding) {
            (activity as AppCompatActivity).apply {
                setSupportActionBar(noteEditToolbar)
                supportActionBar?.setDisplayShowTitleEnabled(false)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
            }

            holder?.let {
                etHeader.setText(it.header)
                etDesc.setText(it.desc)
                etBody.setText(it.body)
                noteID = it.id
                oldKey = it.image
                viewModel.loadImage(oldKey)
            }

            image.setOnClickListener {
                if (curBitmap == null) {
                    viewModel.onImageClick()
                } else {
                    dialog?.show()
                }
            }

        }

        viewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            activity?.contentResolver?.let {
                curBitmap = MediaStore.Images.Media.getBitmap(it, uri)
                loadBitmap()
            }
        }

        viewModel.imageIntent.observe(viewLifecycleOwner) {
            resultLauncher.launch(it)
        }

        viewModel.imageBitmap.observe(viewLifecycleOwner) {
            if (it != null) {
                curBitmap = it
                loadBitmap()
            }
        }

        viewModel.openImage.observe(viewLifecycleOwner) {
            when(it) {
                true -> {
                    binding.imageBig.visibility = View.VISIBLE
                    binding.imageBig.setImageBitmap(curBitmap)
                }

                false -> {
                    binding.imageBig.visibility = View.GONE
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_apply_note -> {
                with(binding) {
                    viewModel.onApplyClick(
                        NoteRecyclerHolder(
                            id = noteID,
                            header = etHeader.text.toString(),
                            desc = etDesc.text.toString(),
                            body = etBody.text.toString(),
                            image = oldKey
                        ),
                        curBitmap
                    )
                }
                true
            }

            R.id.action_delete_note -> {
                viewModel.onDelClick(noteID)
                true
            }

            android.R.id.home -> {
                viewModel.onCancelClick()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadBitmap() {
        Glide.with(this)
            .load(curBitmap)
            .centerCrop()
            .into(binding.image)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_editor, menu)
    }
}
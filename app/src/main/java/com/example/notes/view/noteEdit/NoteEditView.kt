package com.example.notes.view.noteEdit

import android.os.Bundle
import android.view.View
import com.example.notes.R
import com.example.notes.application.MainApplication
import com.example.notes.base.BaseView
import com.example.notes.databinding.FragNoteEditBinding
import com.example.notes.presenter.noteEdit.NoteViewModel
import com.example.notes.view.entities.NoteRecyclerHolder
import javax.inject.Inject

private const val ARG_HOLDER = "ARG_HOLDER"

class NoteEditView @Inject constructor(
    override val viewModel: NoteViewModel
): BaseView<NoteViewModel>(R.layout.frag_note_edit) {
    companion object {
        fun newInstance(noteRecyclerHolder: NoteRecyclerHolder) = MainApplication
            .instance
            .presenterComponent
            .getNoteEditView().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_HOLDER, noteRecyclerHolder)
                }
        }
    }

    private var holder: NoteRecyclerHolder? = null
    private var noteID: Int = 0
    private lateinit var binding: FragNoteEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
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
                viewModel.onApplyClick(
                    NoteRecyclerHolder(
                        id = noteID,
                        header = etHeader.text.toString(),
                        desc = etDesc.text.toString(),
                        body = etBody.text.toString(),
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
            }
        }
    }
}
package com.example.notes.view.noteEdit

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.notes.R
import com.example.notes.base.BaseView
import com.example.notes.databinding.FragNoteEditBinding
import com.example.notes.di.Injector
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.presenter.noteEdit.NoteViewModel
import com.example.notes.view.editor.EditorView
import org.joda.time.DateTime
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
    private var noteID: Long = 0
    private var _binding: FragNoteEditBinding? = null
    private val binding get() = _binding!!
    private var oldKey = listOf<String>()
    private lateinit var creationDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.component.inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            holder = it.getParcelable(ARG_HOLDER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragNoteEditBinding.inflate(inflater)

        with(binding) {
            tbNoteEdit.inflateMenu(R.menu.menu_editor)
            tbNoteEdit.setOnMenuItemClickListener{ item ->
                when(item.itemId) {
                    R.id.action_apply_note -> {
                        with(binding) {
                            if (etHeader.text.toString().isNotBlank()) {
                                viewModel.onApplyClick(
                                    NoteRecyclerHolder(
                                        id = noteID,
                                        header = etHeader.text.toString(),
                                        desc = etDesc.text.toString(),
                                        body = etBody.text.toString(),
                                        image = oldKey,
                                        creationDate=when(creationDate.isBlank()) {
                                            true -> {
                                                DateTime.now().toStringFormat()
                                            }

                                            false -> {
                                                creationDate
                                            }
                                        },
                                        lastEditDate=DateTime.now().toStringFormat()
                                    )
                                )
                            } else {
                                activity?.let {
                                    etHeader.setBackgroundColor(it.applicationContext.resources.getColor(R.color.errorRed))
                                }
                            }
                        }
                        true
                    }

//                    android.R.id.home -> {
//                        viewModel.onCancelClick()
//                        true
//                    }

                    else -> false
                }
            }

            holder?.let {
                etHeader.setText(it.header)
                etDesc.setText(it.desc)
                etBody.setText(it.body)
                noteID = it.id
                creationDate = it.creationDate
                oldKey=it.image
            }
        }

        viewModel.hideKeyboard.observe(viewLifecycleOwner) {
            val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }

        viewModel.goBack.observe(viewLifecycleOwner) {
            (parentFragment as? EditorView)?.onEditorBack()
        }

        viewModel.result.observe(viewLifecycleOwner) {
            (parentFragment as? EditorView)?.saveNote(it)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun DateTime.toStringFormat(): String = this.toString("dd.MM HH:mm")
}
package com.example.notes.view.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.notes.R
import com.example.notes.base.ResultFragment
import com.example.notes.databinding.FragEditorBinding
import com.example.notes.di.Injector
import com.example.notes.presenter.editor.EditorViewModel
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.view.gallery.GalleryView
import com.example.notes.view.noteEdit.NoteEditView
import javax.inject.Inject

private const val ARG_HOLDER = "ARG_HOLDER"

class EditorView: ResultFragment<EditorViewModel>(R.layout.frag_editor) {
    companion object {
        fun newInstance(noteRecyclerHolder: NoteRecyclerHolder) = EditorView().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_HOLDER, noteRecyclerHolder)
            }
        }

        fun newInstance() = EditorView()
    }

    @Inject override lateinit var viewModel: EditorViewModel

    private var _binding: FragEditorBinding? = null
    private val binding get() = _binding!!
    private var holder: NoteRecyclerHolder? = null

    fun setImages(list: List<String>) {
        holder?.apply {
            this.image = list
        }
    }

    fun saveNote(noteRecyclerHolder: NoteRecyclerHolder) {
        holder?.let {
            noteRecyclerHolder.image = it.image
        }
        viewModel.sendResult(noteRecyclerHolder)
    }

    fun onEditorBack() {
        viewModel.onEditorBack()
    }

    fun onGalleryBack() {
        viewModel.onGalleryBack()
    }

    fun isGalleryOpen(value: Boolean) {
        viewModel.setIsGalleryState(value)
    }

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
        _binding = FragEditorBinding.inflate(inflater)

        with(binding) {

            motionLayout.setTransitionListener( object: MotionLayout.TransitionListener {
                override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) { }

                override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) { }

                override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                    when (p1) {
                        R.id.end -> (childFragmentManager.findFragmentById(galleryFrame.id) as GalleryView).isOpen = true
                        R.id.start -> (childFragmentManager.findFragmentById(galleryFrame.id) as GalleryView).isOpen = false
                    }
                }

                override fun onTransitionTrigger(
                    p0: MotionLayout?,
                    p1: Int,
                    positive: Boolean,
                    p3: Float
                ) { }

            })
            childFragmentManager
                .beginTransaction()
                .add(
                    editorFrame.id,
                    NoteEditView.newInstance(
                        holder ?:
                        NoteRecyclerHolder(0, "", "", "", listOf(), "", "")
                    )
                )
                .add(
                    galleryFrame.id,
                    GalleryView.newInstance(holder?.image ?: listOf())
                )
                .commit()
        }

        viewModel.closeGallery.observe(viewLifecycleOwner) {
            with(binding) {
                motionLayout.transitionToStart()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
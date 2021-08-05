package com.example.notes.cleanArchitecture.view.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.notes.R
import com.example.notes.classes.base.baseResultFragment.ResultFragment
import com.example.notes.cleanArchitecture.presenter.editor.EditorViewModel
import com.example.notes.cleanArchitecture.presenter.entities.PresenterNoteEntity
import com.example.notes.cleanArchitecture.view.gallery.GalleryView
import com.example.notes.cleanArchitecture.view.noteEdit.NoteEditView
import com.example.notes.databinding.FragEditorBinding
import com.example.notes.di.Injector
import javax.inject.Inject

private const val ARG_HOLDER = "ARG_HOLDER"

class EditorView: ResultFragment<EditorViewModel>(R.layout.frag_editor) {
    companion object {
        fun newInstance(presenterNoteEntity: PresenterNoteEntity) = EditorView().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_HOLDER, presenterNoteEntity)
            }
        }

        fun newInstance() = EditorView()
    }

    @Inject override lateinit var viewModel: EditorViewModel

    private var _binding: FragEditorBinding? = null
    private val binding get() = _binding!!

    private var entityPresenter: PresenterNoteEntity? = null

    fun setImages(list: List<String>) {
        entityPresenter?.apply {
            this.image = list
        }
    }

    fun saveNote(presenterNoteEntity: PresenterNoteEntity) {
        entityPresenter?.let {
            presenterNoteEntity.image = it.image
        }
        viewModel.sendResult(presenterNoteEntity)
    }

    fun onEditorBack() {
        viewModel.onEditorBack()
    }

    fun onGalleryBack() {
        viewModel.onGalleryBack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.component.inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            entityPresenter = it.getParcelable(ARG_HOLDER)
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
                        entityPresenter ?:
                        PresenterNoteEntity("", "", "", "", listOf(), "", "")
                    )
                )
                .add(
                    galleryFrame.id,
                    GalleryView.newInstance(entityPresenter?.image ?: listOf())
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
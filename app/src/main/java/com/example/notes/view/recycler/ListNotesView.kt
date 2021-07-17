package com.example.notes.view.recycler

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.base.BaseView
import com.example.notes.databinding.FragRecyclerBinding
import com.example.notes.di.Injector
import com.example.notes.presenter.recycler.NoteRecyclerAdapter
import com.example.notes.presenter.recycler.RecyclerViewModel
import javax.inject.Inject

class ListNotesView: BaseView<RecyclerViewModel>(R.layout.frag_recycler) {
    companion object {
        fun newInstance() = ListNotesView()
    }

    @Inject override lateinit var viewModel: RecyclerViewModel

    private val adapter = NoteRecyclerAdapter()
    private lateinit var binding: FragRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragRecyclerBinding.bind(view)

        with(binding) {
            recycler.layoutManager = LinearLayoutManager(context).apply { orientation=LinearLayoutManager.VERTICAL }
            adapter.recyclerViewModel = viewModel
            recycler.adapter = adapter

            btnAddNote.setOnClickListener { viewModel.onAddNoteClick() }
        }

        viewModel.noteList.observe(viewLifecycleOwner) {
            adapter.notesList = it
        }
    }
}
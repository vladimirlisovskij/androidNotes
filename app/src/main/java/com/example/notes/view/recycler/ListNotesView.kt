package com.example.notes.view.recycler

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.base.BaseView
import com.example.notes.databinding.FragRecyclerBinding
import com.example.notes.di.Injector
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.presenter.recycler.NoteRecyclerAdapter
import com.example.notes.presenter.recycler.RecyclerViewModel
import com.labo.kaji.fragmentanimations.CubeAnimation
import com.labo.kaji.fragmentanimations.FlipAnimation
import com.labo.kaji.fragmentanimations.MoveAnimation
import com.labo.kaji.fragmentanimations.PushPullAnimation
import javax.inject.Inject

class ListNotesView: BaseView<RecyclerViewModel>(R.layout.frag_recycler) {
    companion object {
        fun newInstance() = ListNotesView()
    }

    @Inject override lateinit var viewModel: RecyclerViewModel
    @Inject lateinit var adapter: NoteRecyclerAdapter

    private lateinit var binding: FragRecyclerBinding
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragRecyclerBinding.bind(view)

        setHasOptionsMenu(true)

        with(binding) {
            (activity as AppCompatActivity).apply {
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }

            adapter.recyclerViewModel = viewModel
            recycler.layoutManager = LinearLayoutManager(context).apply { orientation=LinearLayoutManager.VERTICAL }
            recycler.adapter = adapter

        }

        viewModel.selectedMode.observe(viewLifecycleOwner) {
            (activity as AppCompatActivity).apply {
                supportActionBar?.setDisplayHomeAsUpEnabled(it)
                supportActionBar?.setDisplayShowHomeEnabled(it)
            }
            menu.findItem(R.id.action_del_notes).isVisible = it
            menu.findItem(R.id.action_select_all).isVisible = it
            menu.findItem(R.id.action_add_note).isVisible = !it
            adapter.isSelectedMode = it

            if (it == false) {
                for (i in 0 until binding.recycler.childCount) {
                    (binding.recycler.getChildViewHolder(binding.recycler.getChildAt(i)) as NoteRecyclerAdapter.NoteViewHolder)
                        .isSelected = false
                }
            }
        }

        viewModel.noteList.observe(viewLifecycleOwner) {
            adapter.notesList = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_add_note -> {
                viewModel.onAddNoteClick()
                true
            }

            R.id.action_select_all -> {
                for (i in 0 until binding.recycler.childCount) {
                    (binding.recycler.getChildViewHolder(binding.recycler.getChildAt(i)) as NoteRecyclerAdapter.NoteViewHolder)
                        .isSelected = true
                }
                true
            }

            android.R.id.home -> {
                viewModel.onNavigationBack()
                true
            }

            R.id.action_del_notes -> {
                viewModel.onDeleteClick(mutableListOf<NoteRecyclerHolder>().apply {
                    for (i in 0 until binding.recycler.childCount) {
                        (binding.recycler.getChildViewHolder(binding.recycler.getChildAt(i)) as NoteRecyclerAdapter.NoteViewHolder).let {
                            if (it.isSelected) {
                                it.note?.let { holder ->
                                    this.add(holder)
                                }
                            }
                        }
                    }
                })
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_recycler, menu)
        this.menu = menu
        menu.findItem(R.id.action_del_notes).isVisible = false
        menu.findItem(R.id.action_select_all).isVisible = false
    }
}
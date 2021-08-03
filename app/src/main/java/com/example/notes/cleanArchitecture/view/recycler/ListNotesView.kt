package com.example.notes.cleanArchitecture.view.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.classes.base.baseFragment.BaseView
import com.example.notes.databinding.FragRecyclerBinding
import com.example.notes.di.Injector
import com.example.notes.cleanArchitecture.presenter.entities.NoteRecyclerHolder
import com.example.notes.cleanArchitecture.presenter.recycler.NoteRecyclerAdapter
import com.example.notes.cleanArchitecture.presenter.recycler.RecyclerViewModel
import javax.inject.Inject

class ListNotesView: BaseView<RecyclerViewModel>(R.layout.frag_recycler) {
    companion object {
        fun newInstance() = ListNotesView()
    }

    @Inject override lateinit var viewModel: RecyclerViewModel
    @Inject lateinit var adapter: NoteRecyclerAdapter

    private var _binding: FragRecyclerBinding? = null
    private val binding get() = _binding!!
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragRecyclerBinding.inflate(inflater)

        with(binding) {
            toolbar.inflateMenu(R.menu.menu_recycler)
            toolbar.setNavigationOnClickListener {
                viewModel.onNavigationBack()
            }
            toolbar.setOnMenuItemClickListener { item ->
                when(item.itemId) {
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

                    else -> false
                }
            }
            menu = toolbar.menu
            menu.findItem(R.id.action_del_notes).isVisible = false
            menu.findItem(R.id.action_select_all).isVisible = false

            adapter.longTabListener = this@ListNotesView::onLongTab
            adapter.tabListener = this@ListNotesView::onTab
            recycler.layoutManager = LinearLayoutManager(context).apply { orientation=LinearLayoutManager.VERTICAL }
            recycler.adapter = adapter
        }

        viewModel.selectedMode.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.toolbar.setNavigationIcon(R.drawable.ic_back)
            } else {
                binding.toolbar.navigationIcon = null
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

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onLongTab() {
        viewModel.onLongTab()
    }

    private fun onTab(noteRecyclerHolder: NoteRecyclerHolder) {
        viewModel.onItemClick(noteRecyclerHolder)
    }
}
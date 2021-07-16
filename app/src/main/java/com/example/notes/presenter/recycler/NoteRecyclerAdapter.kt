package com.example.notes.presenter.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.databinding.ItemRecyclerNoteBinding
import com.example.notes.view.entities.NoteRecyclerHolder

class NoteRecyclerAdapter: RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder>() {
    inner class NoteViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRecyclerNoteBinding.bind(itemView)

        var note: NoteRecyclerHolder? = null
            set (dataFormContainerList) {
                field = dataFormContainerList
                field?.let {
                    with(binding) {
                        tvHeader.text = it.header
                        tvBody.text = it.desc
                    }
                }
            }

        init {
            with(binding) {
                layout.setOnClickListener {
                    note?.let { recyclerViewModel?.onItemClick(it) }
                }
            }
        }
    }

    var recyclerViewModel: RecyclerViewModel? = null

    var notesList: List<NoteRecyclerHolder>? = null
        set (dataFormContainerList) {
            field = dataFormContainerList
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        notesList?.let {
            holder.note = it[position]
        }
    }

    override fun getItemCount(): Int = notesList?.size ?: 0
}
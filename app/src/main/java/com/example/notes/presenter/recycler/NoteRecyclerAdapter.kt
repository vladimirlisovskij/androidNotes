package com.example.notes.presenter.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.application.MainApplication
import com.example.notes.databinding.ItemRecyclerNoteBinding
import com.example.notes.presenter.entities.NoteRecyclerHolder
import javax.inject.Inject

class NoteRecyclerAdapter @Inject constructor(): RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder>() {
    inner class NoteViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRecyclerNoteBinding.bind(itemView)
        private val res = MainApplication.instance.resources

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


        var isSelected: Boolean = false
            set(value) {
                field = value
                if (field) {
                    binding.tvHeader.setTextColor(res.getColor(R.color.RecyclerItem_Header_Selected))
                    binding.tvBody.setTextColor(res.getColor(R.color.RecyclerItem_Body_Selected))
                    binding.layout.setBackgroundResource(R.drawable.bg_item_recycler_selected)
                } else {
                    binding.tvHeader.setTextColor(res.getColor(R.color.RecyclerItem_Header_Default))
                    binding.tvBody.setTextColor(res.getColor(R.color.RecyclerItem_Body_Default))
                    binding.layout.setBackgroundResource(R.drawable.bg_item_recycler)
                }
            }

        init {
            isSelected = false
            with(binding) {
                layout.setOnLongClickListener {
                    note?.let {
                        if (isSelectedMode) {
                            isSelected = !isSelected
                        } else {
                            isSelected = true
                            recyclerViewModel?.onLongTab()
                        }
                    }
                    true
                }
                layout.setOnClickListener {
                    if (isSelectedMode) {
                        isSelected = !isSelected
                    } else {
                        note?.let { recyclerViewModel?.onItemClick(it) }
                    }
                }
            }
        }
    }

    var isSelectedMode = false

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
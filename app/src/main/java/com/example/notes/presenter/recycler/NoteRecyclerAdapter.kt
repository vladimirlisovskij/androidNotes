package com.example.notes.presenter.recycler

import android.graphics.Bitmap
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
                        tvCreationDate.text=itemView.resources.getString(R.string.created, it.creationDate)
                        tvLastEditDate.text=itemView.resources.getString(R.string.edited, it.lastEditDate)
                    }
                }
            }


        var isSelected: Boolean = false
            set(value) {
                field = value
                with(binding) {
                    if (field) {
                        tvHeader.setTextColor(res.getColor(R.color.RecyclerItem_Header_Selected))
                        tvBody.setTextColor(res.getColor(R.color.RecyclerItem_Body_Selected))
                        tvCreationDate.setTextColor(res.getColor(R.color.RecyclerItem_Body_Selected))
                        tvLastEditDate.setTextColor(res.getColor(R.color.RecyclerItem_Body_Selected))
                        layout.setBackgroundResource(R.drawable.bg_item_recycler_selected)
                    } else {
                        tvHeader.setTextColor(res.getColor(R.color.RecyclerItem_Header_Default))
                        tvBody.setTextColor(res.getColor(R.color.RecyclerItem_Body_Default))
                        tvCreationDate.setTextColor(res.getColor(R.color.RecyclerItem_Body_Default))
                        tvLastEditDate.setTextColor(res.getColor(R.color.RecyclerItem_Body_Default))
                        layout.setBackgroundResource(R.drawable.bg_item_recycler)
                    }
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
                            longTabListener?.invoke()
                        }
                    }
                    true
                }
                layout.setOnClickListener {
                    if (isSelectedMode) {
                        isSelected = !isSelected
                    } else {
                        note?.let { tabListener?.invoke(it) }
                    }
                }
            }
        }
    }

    var isSelectedMode = false

    var longTabListener: (() -> Unit)? = null
    var tabListener: ((NoteRecyclerHolder) -> Unit)? = null

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
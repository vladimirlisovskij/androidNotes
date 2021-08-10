package com.example.notes.presenter.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.presenter.entities.PresenterNoteEntity
import com.example.notes.databinding.ItemRecyclerNoteBinding
import com.example.notes.presenter.entities.NoteRecyclerItems
import javax.inject.Inject


class NoteRecyclerAdapter @Inject constructor(
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val PROGRESS_TYPE = 0
        private const val NOTE_TYPE = 1
    }

    inner class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRecyclerNoteBinding.bind(itemView)
        private val res = context.resources

        var presenterNote: PresenterNoteEntity? = null
            set(dataFormContainerList) {
                field = dataFormContainerList
                field?.let {
                    with(binding) {
                        tvHeader.text = it.header
                        tvBody.text = it.desc
                        tvCreationDate.text =
                            itemView.resources.getString(R.string.created, it.creationDate)
                        tvLastEditDate.text =
                            itemView.resources.getString(R.string.edited, it.lastEditDate)
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
                        card.setCardBackgroundColor(res.getColor(R.color.bg_note_list_item_selected))
                    } else {
                        tvHeader.setTextColor(res.getColor(R.color.RecyclerItem_Header_Default))
                        tvBody.setTextColor(res.getColor(R.color.RecyclerItem_Body_Default))
                        tvCreationDate.setTextColor(res.getColor(R.color.RecyclerItem_Body_Default))
                        tvLastEditDate.setTextColor(res.getColor(R.color.RecyclerItem_Body_Default))
                        card.setCardBackgroundColor(res.getColor(R.color.bg_note_list_item_deselected))
                    }
                }
            }

        init {
            isSelected = false
            with(binding) {
                card.setOnLongClickListener {
                    presenterNote?.let {
                        if (isSelectedMode) {
                            isSelected = !isSelected
                        } else {
                            isSelected = true
                            longTabListener?.invoke()
                        }
                    }
                    true
                }
                card.setOnClickListener {
                    if (isSelectedMode) {
                        isSelected = !isSelected
                    } else {
                        presenterNote?.let { tabListener?.invoke(it) }
                    }
                }
            }
        }
    }

    var isSelectedMode = false

    var longTabListener: (() -> Unit)? = null
    var tabListener: ((PresenterNoteEntity) -> Unit)? = null

    var notesList: List<NoteRecyclerItems>? = null
        set(dataFormContainerList) {
            field = dataFormContainerList
            notifyDataSetChanged()
        }


    override fun getItemViewType(position: Int): Int {
        return notesList?.let {
            when (it[position]) {
                is NoteRecyclerItems.ProgressItem -> PROGRESS_TYPE
                is NoteRecyclerItems.NoteItem -> NOTE_TYPE
            }
        } ?: PROGRESS_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NOTE_TYPE -> {
                NoteViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_recycler_note, parent, false)
                )
            }
            PROGRESS_TYPE -> {
                ProgressViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_circle_progress, parent, false)
                )
            }
            else -> {
                ProgressViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_circle_progress, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            NOTE_TYPE -> {
                notesList?.let {
                    (holder as NoteViewHolder).presenterNote = (it[position] as NoteRecyclerItems.NoteItem).data
                }
            }
        }
    }

    override fun getItemCount(): Int = notesList?.size ?: 0
}
package com.example.notes.presenter.gallery

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notes.R
import com.example.notes.databinding.ItemGalleryBinding
import javax.inject.Inject

class GalleryAdapter @Inject constructor(): RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {
    inner class ImageViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGalleryBinding.bind(itemView)

        var image: Bitmap? = null
            set (value) {
                field = value
                Glide.with(itemView)
                    .load(field)
                    .centerCrop()
                    .into(binding.imageView)
            }

        var isSelected: Boolean = false
            set(value) {
                field = value
                with(binding) {
                    if (field) {
                        itemImageLayout.setBackgroundResource(R.drawable.bg_item_recycler_selected)
                    } else {
                        itemImageLayout.setBackgroundResource(R.drawable.bg_item_recycler)
                    }
                }
            }

        init {
            isSelected = false
            with(binding) {
                itemImageLayout.setOnLongClickListener {
                    if (isAdapterSelectedMode) {
                        isSelected = !isSelected
                    } else {
                        isSelected = true
                        galleryViewModel?.onLongTab()
                    }
                    true
                }

                itemImageLayout.setOnClickListener {
                    if (isAdapterSelectedMode) {
                        isSelected = !isSelected
                    } else {
                        image?.let { galleryViewModel?.onItemClick(it) }
                    }
                }
            }
        }
    }

    var galleryViewModel: GalleryViewModel? = null

    var isAdapterSelectedMode = false

    var bitmapList: List<Bitmap>? = null
        set (dataFormContainerList) {
            field = dataFormContainerList
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        bitmapList?.let {
            holder.image = it[position]
        }
    }

    override fun getItemCount(): Int = bitmapList?.size ?: 0
}
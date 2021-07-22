package com.example.notes.view.gallery

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notes.R
import com.example.notes.base.BaseView
import com.example.notes.base.ResultFragment
import com.example.notes.databinding.FragGaleryRecyclerBinding
import com.example.notes.di.Injector
import com.example.notes.presenter.gallery.GalleryAdapter
import com.example.notes.presenter.gallery.GalleryViewModel
import com.example.notes.presenter.recycler.NoteRecyclerAdapter
import javax.inject.Inject

const val ARG_REFS = "ARG_REFS"

class GalleryView
    : ResultFragment<GalleryViewModel>(R.layout.frag_galery_recycler)
{
    companion object {
        fun newInstance(refs: List<String>) = GalleryView().apply {
            arguments = Bundle().apply {
                putStringArrayList(ARG_REFS, ArrayList(refs))
            }
        }
    }

    @Inject override lateinit var viewModel: GalleryViewModel
    @Inject lateinit var adapter: GalleryAdapter

    private lateinit var binding: FragGaleryRecyclerBinding
    private lateinit var menu: Menu
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        viewModel.onActivityResult(it)
    }
    private var bitmaps = mutableListOf<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragGaleryRecyclerBinding.bind(view)
        setHasOptionsMenu(true)

        with(binding) {
            (activity as AppCompatActivity).apply {
                setSupportActionBar(toolbarGallery)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }

            adapter.galleryViewModel = viewModel
            recyclerGallery.layoutManager =  GridLayoutManager(requireContext(), 2)
            recyclerGallery.adapter = adapter
        }

        arguments?.getStringArrayList(ARG_REFS)?.let {
            viewModel.getBitmaps(it)
        }

        viewModel.selectionMode.observe(viewLifecycleOwner) {
            menu.findItem(R.id.action_select_images).isVisible = it
            menu.findItem(R.id.action_delete_image).isVisible = it

            menu.findItem(R.id.action_apply_gallery).isVisible = !it
            menu.findItem(R.id.action_add_image).isVisible = !it

            adapter.isAdapterSelectedMode = it

            if (it == false) {
                with(binding) {
                    for (i in 0 until recyclerGallery.childCount) {
                        (recyclerGallery.getChildViewHolder(recyclerGallery.getChildAt(i))
                                as GalleryAdapter.ImageViewHolder)
                            .isSelected = false
                    }
                }
            }
        }

        viewModel.bitmapList.observe(viewLifecycleOwner) {
            bitmaps = it.toMutableList()
            adapter.bitmapList = bitmaps
        }

        viewModel.imageIntent.observe(viewLifecycleOwner) {
            resultLauncher.launch(it)
        }

        viewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            activity?.contentResolver?.let {
                bitmaps.add(MediaStore.Images.Media.getBitmap(it, uri))
                adapter.bitmapList = bitmaps
            }
        }

        viewModel.openImage.observe(viewLifecycleOwner) {
            val isNull = it == null
            menu.findItem(R.id.action_apply_gallery).isVisible = isNull
            menu.findItem(R.id.action_add_image).isVisible = isNull
            with(binding) {
                if (isNull) {
                    imageBig.visibility = View.GONE
                } else {
                    imageBig.visibility = View.VISIBLE
                    imageBig.setImageBitmap(it)
                }
            }
        }

        viewModel.showProgress.observe(viewLifecycleOwner) {
            when(it) {
                true -> {
                    binding.noteEditProgressBar.visibility = View.VISIBLE
                }

                false -> {
                    binding.noteEditProgressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_apply_gallery -> {
                viewModel.onApplyClick(bitmaps)
                true
            }

            R.id.action_add_image -> {
                viewModel.onImageAdd()
                true
            }

            R.id.action_select_images -> {
                with(binding) {
                    for (i in 0 until recyclerGallery.childCount) {
                        (recyclerGallery.getChildViewHolder(recyclerGallery.getChildAt(i))
                                as GalleryAdapter.ImageViewHolder)
                            .isSelected = true
                    }
                }
                true
            }

            android.R.id.home -> {
                viewModel.onBackClick()
                true
            }

            R.id.action_delete_image -> {
                bitmaps.clear()
                with(binding) {
                    for (i in 0 until recyclerGallery.childCount) {
                        (recyclerGallery.getChildViewHolder(recyclerGallery.getChildAt(i))
                                as GalleryAdapter.ImageViewHolder).let {
                            if (!it.isSelected) {
                                it.image?.let { image ->
                                    bitmaps.add(image)
                                }
                            }
                        }
                    }
                }
                adapter.bitmapList = bitmaps
                viewModel.onDel()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_gallery, menu)
        this.menu = menu
        menu.findItem(R.id.action_delete_image).isVisible = false
        menu.findItem(R.id.action_select_images).isVisible = false
    }
}
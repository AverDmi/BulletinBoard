package com.dimthomas.bulletinboard.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimthomas.bulletinboard.R
import com.dimthomas.bulletinboard.databinding.ListImageFragmentBinding
import com.dimthomas.bulletinboard.utils.ImageManager
import com.dimthomas.bulletinboard.utils.ImagePicker
import com.dimthomas.bulletinboard.utils.ImagePicker.REQUEST_CODE_GET_IMAGES
import com.dimthomas.bulletinboard.utils.ItemTouchMoveCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ImageListFragment(private val fragmentCloseInterface: FragmentCloseInterface, private val newList: ArrayList<String>?): Fragment() {

    private lateinit var binding: ListImageFragmentBinding
    val adapter = SelectImageRvAdapter()
    val dragCallback = ItemTouchMoveCallback(adapter)
    val touchHelper = ItemTouchHelper(dragCallback)
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListImageFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        binding.selectImageRv.layoutManager = LinearLayoutManager(activity)
        binding.selectImageRv.adapter = adapter
        touchHelper.attachToRecyclerView(binding.selectImageRv)

        if (newList != null) {
            job = CoroutineScope(Dispatchers.Main).launch {
                val bitmapList = ImageManager.imageResize(newList)
                adapter.updateAdapter(bitmapList, true)
            }
        }
    }

    fun updateAdapterFromEdit(bitmapList: List<Bitmap>) {
        adapter.updateAdapter(bitmapList, true)
    }

    override fun onDetach() {
        super.onDetach()
        fragmentCloseInterface.onFragmentClose(adapter.mainArray)
        job?.cancel()
    }

    private fun setUpToolbar() {
        binding.tb.inflateMenu(R.menu.menu_choose_image)
        val deleteItem = binding.tb.menu.findItem(R.id.delete_image)
        val addImageItem = binding.tb.menu.findItem(R.id.add_image)

        binding.tb.setNavigationOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }

        deleteItem.setOnMenuItemClickListener {
            adapter.updateAdapter(ArrayList(), true)
            true
        }

        addImageItem.setOnMenuItemClickListener {
            val imageCount = ImagePicker.MAX_IMAGE_COUNT - adapter.mainArray.size
            ImagePicker.getImages(activity as AppCompatActivity, imageCount, REQUEST_CODE_GET_IMAGES)
            true
        }
    }

    fun updateAdapter(newList: ArrayList<String>) {

        job = CoroutineScope(Dispatchers.Main).launch {
            val bitmapList = ImageManager.imageResize(newList)
            adapter.updateAdapter(bitmapList, false)
        }
    }

    fun setSingleImage(uri: String, position: Int) {

        job = CoroutineScope(Dispatchers.Main).launch {
            val bitmapList = ImageManager.imageResize(listOf(uri))
            adapter.mainArray[position] = bitmapList[0]
            adapter.notifyDataSetChanged()
        }


    }
}
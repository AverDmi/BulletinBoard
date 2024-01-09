package com.dimthomas.bulletinboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimthomas.bulletinboard.R
import com.dimthomas.bulletinboard.utils.ItemTouchMoveCallback

class ImageListFragment(private val fragmentCloseInterface: FragmentCloseInterface, private val newList: ArrayList<String>): Fragment() {

    val adapter = SelectImageRvAdapter()
    val dragCallback = ItemTouchMoveCallback(adapter)
    val touchHelper = ItemTouchHelper(dragCallback)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_image_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backBtn = view.findViewById<Button>(R.id.back_btn)
        val rcView = view.findViewById<RecyclerView>(R.id.select_image_rv)
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
        touchHelper.attachToRecyclerView(rcView)
        val updateList = ArrayList<SelectImageItem>()
        for (n in 0 until newList.size) {
            updateList.add(SelectImageItem(n.toString(), newList[n]))
        }
        adapter.updateAdapter(updateList)
        backBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragmentCloseInterface.onFragmentClose()
    }
}
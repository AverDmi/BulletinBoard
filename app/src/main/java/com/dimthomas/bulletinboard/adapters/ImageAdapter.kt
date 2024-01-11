package com.dimthomas.bulletinboard.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dimthomas.bulletinboard.R
import com.dimthomas.bulletinboard.fragments.SelectImageItem

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ImageHolder>() {
    private val mainArray = ArrayList<SelectImageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_adapter_item, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int {
        return mainArray.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(mainArray[position].imageUri)
    }

    class ImageHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private lateinit var itemIv: ImageView

        fun setData(uri: String) {
            itemIv = itemView.findViewById(R.id.item_iv)
            itemIv.setImageURI(Uri.parse(uri))
        }

    }

    fun update(newList: ArrayList<SelectImageItem>) {
        mainArray.clear()
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }
}
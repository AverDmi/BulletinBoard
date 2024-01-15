package com.dimthomas.bulletinboard.adapters

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dimthomas.bulletinboard.R

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ImageHolder>() {
    val mainArray = ArrayList<Bitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_adapter_item, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int {
        return mainArray.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(mainArray[position])
    }

    class ImageHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private lateinit var itemIv: ImageView

        fun setData(bitMap: Bitmap) {
            itemIv = itemView.findViewById(R.id.item_iv)
            itemIv.setImageBitmap(bitMap)
        }

    }

    fun update(newList: ArrayList<Bitmap>) {
        mainArray.clear()
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }
}
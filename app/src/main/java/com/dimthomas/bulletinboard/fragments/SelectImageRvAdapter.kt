package com.dimthomas.bulletinboard.fragments

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dimthomas.bulletinboard.R
import com.dimthomas.bulletinboard.act.EditAdsActivity
import com.dimthomas.bulletinboard.utils.ImagePicker
import com.dimthomas.bulletinboard.utils.ImagePicker.REQUEST_CODE_GET_SINGLE_IMAGE
import com.dimthomas.bulletinboard.utils.ItemTouchMoveCallback

class SelectImageRvAdapter: RecyclerView.Adapter<SelectImageRvAdapter.ImageHolder>(),  ItemTouchMoveCallback.ItemTouchAdapter{

    val mainArray = ArrayList<Bitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_image_fragment_item, parent, false)
        return ImageHolder(view, parent.context, this)
    }

    override fun getItemCount(): Int {
        return mainArray.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(mainArray[position])
    }

    override fun onMove(startPosition: Int, targetPosition: Int) {
        val targetItem = mainArray[targetPosition]
        mainArray[targetPosition] = mainArray[startPosition]
//        val titleStart = mainArray[targetPosition].title
//        mainArray[targetPosition].title = targetItem.title
        mainArray[startPosition] = targetItem
//        mainArray[startPosition].title = titleStart
        notifyItemMoved(startPosition, targetPosition)
    }

    override fun onClear() {
        notifyDataSetChanged()
    }

    class ImageHolder(itemView: View, val context: Context, val adapter: SelectImageRvAdapter): RecyclerView.ViewHolder(itemView) {

        lateinit var titleTv: TextView
        lateinit var image: ImageView
        lateinit var editImageIb: ImageButton
        lateinit var deleteImageIb: ImageButton

        fun setData(bitMap: Bitmap) {

            titleTv = itemView.findViewById(R.id.title_tv)
            image = itemView.findViewById(R.id.imageContent)
            editImageIb = itemView.findViewById(R.id.edit_image_ib)
            deleteImageIb = itemView.findViewById(R.id.delete_ib)


            editImageIb.setOnClickListener {
                ImagePicker.getImages(context as EditAdsActivity, 1, REQUEST_CODE_GET_SINGLE_IMAGE)
                context.editImagePosition = adapterPosition
            }

            deleteImageIb.setOnClickListener {
                adapter.mainArray.removeAt(adapterPosition)
                adapter.notifyItemRemoved(adapterPosition)
                for (n in 0 until adapter.mainArray.size) {
                    adapter.notifyItemChanged(n)
                }
            }

            titleTv.text = context.resources.getStringArray(R.array.title_array)[adapterPosition]
            image.setImageBitmap(bitMap)
        }
    }

    fun updateAdapter(newList: List<Bitmap>, needClear: Boolean) {
        if (needClear) {
            mainArray.clear()
        }
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }


}
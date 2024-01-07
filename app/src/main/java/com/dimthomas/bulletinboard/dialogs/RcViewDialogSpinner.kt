package com.dimthomas.bulletinboard.dialogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dimthomas.bulletinboard.R

class RcViewDialogSpinner: RecyclerView.Adapter<RcViewDialogSpinner.SpViewHolder>() {

    private val mainList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sp_list_item, parent, false)
        return SpViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    override fun onBindViewHolder(holder: SpViewHolder, position: Int) {
        holder.setData(mainList[position])
    }

    class SpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(text: String) {
            val spItemTv = itemView.findViewById<TextView>(R.id.sp_item_tv)
            spItemTv.text = text
        }
    }

    fun updateAdapter(list: ArrayList<String>) {
        mainList.clear()
        mainList.addAll(list)
        notifyDataSetChanged()
    }
}
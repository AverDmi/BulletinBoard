package com.dimthomas.bulletinboard.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dimthomas.bulletinboard.R
import com.dimthomas.bulletinboard.act.EditAdsActivity

class RcViewDialogSpinnerAdapter(private var tvSelection: TextView, private var dialog: AlertDialog) :
    RecyclerView.Adapter<RcViewDialogSpinnerAdapter.SpViewHolder>() {

    private val mainList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sp_list_item, parent, false)
        return SpViewHolder(view, tvSelection, dialog)
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    override fun onBindViewHolder(holder: SpViewHolder, position: Int) {
        holder.setData(mainList[position])
    }

    class SpViewHolder(itemView: View, private var tvSelection: TextView, private var dialog: AlertDialog) :
        RecyclerView.ViewHolder(itemView), OnClickListener {

        private var itemText = ""

        fun setData(text: String) {
            val spItemTv = itemView.findViewById<TextView>(R.id.sp_item_tv)
            spItemTv.text = text
            itemText = text
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            tvSelection.text = itemText
            dialog.dismiss()
        }
    }

    fun updateAdapter(list: ArrayList<String>) {
        mainList.clear()
        mainList.addAll(list)
        notifyDataSetChanged()
    }
}
package com.dimthomas.bulletinboard.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimthomas.bulletinboard.R
import com.dimthomas.bulletinboard.utils.CityHelper

class DialogSpinnerHelper {

    fun showSpinnerDialog(context: Context, list: ArrayList<String>) {
        val builder = AlertDialog.Builder(context)
        val rootView = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null)
        val adapter = RcViewDialogSpinner()
        val rcView = rootView.findViewById<RecyclerView>(R.id.sp_view_rc)
        val sv = rootView.findViewById<SearchView>(R.id.spinner_sv)
        rcView.layoutManager = LinearLayoutManager(context)
        rcView.adapter = adapter
        builder.setView(rootView)
        adapter.updateAdapter(list)
        setSearchView(adapter, list, sv)
        builder.show()
    }

    private fun setSearchView(adapter: RcViewDialogSpinner, list: ArrayList<String>, sv: SearchView?) {
        sv?.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val tempList = CityHelper.filterListData(list, newText)
                adapter.updateAdapter(tempList)
                return true
            }

        })
    }

}
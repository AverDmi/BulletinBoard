package com.dimthomas.bulletinboard.dialoghelper

import android.app.Activity
import android.app.AlertDialog
import com.dimthomas.bulletinboard.databinding.ProgressDialogLayoutBinding

object ProgressDialog {

    fun createProgressDialog(activity: Activity): AlertDialog {
        val builder = AlertDialog.Builder(activity)
        val binding = ProgressDialogLayoutBinding.inflate(activity.layoutInflater)
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }

}
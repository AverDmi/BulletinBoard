package com.dimthomas.bulletinboard.dialoghelper

import android.app.AlertDialog
import com.dimthomas.bulletinboard.MainActivity
import com.dimthomas.bulletinboard.R
import com.dimthomas.bulletinboard.accounthelper.AccountHelper
import com.dimthomas.bulletinboard.databinding.SignDialogBinding

class DialogHelper(private val activity: MainActivity) {

    private val accHelper = AccountHelper(activity)

    fun createSignDialog(index: Int) {
        val builder = AlertDialog.Builder(activity)
        val binding = SignDialogBinding.inflate(activity.layoutInflater)
        builder.setView(binding.root)

        if (index == SIGN_UP_STATE) {
            binding.signTitleTv.text = activity.resources.getString(R.string.sign_up)
            binding.signUpInBtn.text = activity.resources.getString(R.string.sign_up_action)
        } else {
            binding.signTitleTv.text = activity.resources.getString(R.string.sign_in)
            binding.signUpInBtn.text = activity.resources.getString(R.string.sign_in_action)
        }

        val dialog = builder.create()

        binding.signUpInBtn.setOnClickListener {
            dialog.dismiss()
            if (index == SIGN_UP_STATE) {
                accHelper.signUpWithEmail(
                    binding.signEmailEd.text.toString(),
                    binding.signPasswordEd.text.toString()
                )
            } else {
                accHelper.signInWithEmail(
                    binding.signEmailEd.text.toString(),
                    binding.signPasswordEd.text.toString()
                )
            }
        }

        dialog.show()
    }

    companion object {
        const val SIGN_UP_STATE = 0
        const val SIGN_IN_STATE = 1
    }
}
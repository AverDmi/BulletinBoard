package com.dimthomas.bulletinboard.dialoghelper

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.dimthomas.bulletinboard.MainActivity
import com.dimthomas.bulletinboard.R
import com.dimthomas.bulletinboard.accounthelper.AccountHelper
import com.dimthomas.bulletinboard.databinding.SignDialogBinding

class DialogHelper(private val activity: MainActivity) {

    val accHelper = AccountHelper(activity)

    fun createSignDialog(index: Int) {
        val builder = AlertDialog.Builder(activity)
        val binding = SignDialogBinding.inflate(activity.layoutInflater)
        builder.setView(binding.root)
        setDialogState(index, binding)

        val dialog = builder.create()

        binding.signUpInBtn.setOnClickListener {
           setOnClickSignUpIn(index, binding, dialog)
        }
        binding.forgetPasswordBtn.setOnClickListener {
            setOnClickResetPassword(binding, dialog)
        }
        binding.googleSignInBtn.setOnClickListener {
            accHelper.signInWithGoogle()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setOnClickResetPassword(binding: SignDialogBinding, dialog: AlertDialog?) {
        if (binding.signEmailEd.text.isNotEmpty()) {
            activity.mAuth.sendPasswordResetEmail(binding.signEmailEd.text.toString()).addOnCompleteListener { task -> 
                if (task.isSuccessful) {
                    Toast.makeText(activity, R.string.email_reset_password_was_sent, Toast.LENGTH_SHORT).show()
                }
            }
            dialog?.dismiss()
        } else {
            binding.dialogMessageTv.visibility = View.VISIBLE
        }
    }

    private fun setOnClickSignUpIn(index: Int, binding: SignDialogBinding, dialog: AlertDialog?) {
        dialog?.dismiss()
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

    private fun setDialogState(index: Int, binding: SignDialogBinding) {
        if (index == SIGN_UP_STATE) {
            binding.signTitleTv.text = activity.resources.getString(R.string.sign_up)
            binding.signUpInBtn.text = activity.resources.getString(R.string.sign_up_action)
        } else {
            binding.signTitleTv.text = activity.resources.getString(R.string.sign_in)
            binding.signUpInBtn.text = activity.resources.getString(R.string.sign_in_action)
            binding.forgetPasswordBtn.visibility = View.VISIBLE
        }
    }

    companion object {
        const val SIGN_UP_STATE = 0
        const val SIGN_IN_STATE = 1
    }
}
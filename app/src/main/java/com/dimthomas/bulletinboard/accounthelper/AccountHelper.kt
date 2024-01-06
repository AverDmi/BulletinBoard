package com.dimthomas.bulletinboard.accounthelper

import android.widget.Toast
import com.dimthomas.bulletinboard.MainActivity
import com.dimthomas.bulletinboard.R
import com.google.firebase.auth.FirebaseUser

class AccountHelper(private val activity: MainActivity) {

    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendEmailVerification(task.result.user!!)
                } else {
                    Toast.makeText(activity, activity.resources.getString(R.string.sign_up_error), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, activity.resources.getString(R.string.send_verification_email_success), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, activity.resources.getString(R.string.send_verification_email_error), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
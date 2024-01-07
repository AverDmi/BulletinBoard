package com.dimthomas.bulletinboard.accounthelper

import android.widget.Toast
import com.dimthomas.bulletinboard.MainActivity
import com.dimthomas.bulletinboard.R
import com.dimthomas.bulletinboard.constants.FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE
import com.dimthomas.bulletinboard.constants.FirebaseAuthConstants.ERROR_INVALID_EMAIL
import com.dimthomas.bulletinboard.constants.FirebaseAuthConstants.ERROR_WEAK_PASSWORD
import com.dimthomas.bulletinboard.constants.FirebaseAuthConstants.ERROR_WRONG_PASSWORD
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class AccountHelper(private val activity: MainActivity) {

    private lateinit var signInClient: GoogleSignInClient

    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendEmailVerification(task.result.user!!)
                    activity.uiUpdate(task.result.user)
                } else {
//                    Toast.makeText(activity, activity.resources.getString(R.string.sign_up_error), Toast.LENGTH_SHORT).show()
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        val exception = task.exception as FirebaseAuthUserCollisionException
                        if (exception.errorCode == ERROR_EMAIL_ALREADY_IN_USE) {
                            Toast.makeText(activity, ERROR_EMAIL_ALREADY_IN_USE, Toast.LENGTH_SHORT).show()
                        }
                    } else if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        val exception = task.exception as FirebaseAuthInvalidCredentialsException
                        if (exception.errorCode == ERROR_INVALID_EMAIL) {
                            Toast.makeText(activity, ERROR_INVALID_EMAIL, Toast.LENGTH_SHORT).show()
                        }
                    }
                    if (task.exception is FirebaseAuthWeakPasswordException) {
                        val exception = task.exception as FirebaseAuthWeakPasswordException
                        if (exception.errorCode == ERROR_WEAK_PASSWORD) {
                            Toast.makeText(activity, ERROR_WEAK_PASSWORD, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    activity.uiUpdate(task.result?.user)
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        val exception = task.exception as FirebaseAuthInvalidCredentialsException
                        if (exception.errorCode == ERROR_INVALID_EMAIL) {
                            Toast.makeText(activity, ERROR_INVALID_EMAIL, Toast.LENGTH_SHORT).show()
                        } else if (exception.errorCode == ERROR_WRONG_PASSWORD) {
                            Toast.makeText(activity, ERROR_WRONG_PASSWORD, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun getSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(com.firebase.ui.auth.R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(activity, gso)
    }

    fun signInWithGoogle() {
        signInClient = getSignInClient()
        val intent = signInClient.signInIntent
        activity.startActivityForResult(intent, SIGN_IN_REQUEST_CODE)
    }

    fun signInFirebaseWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        activity.mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, "Sign in done", Toast.LENGTH_SHORT).show()
                activity.uiUpdate(task.result?.user)
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

    companion object {
        const val SIGN_IN_REQUEST_CODE = 132
    }
}
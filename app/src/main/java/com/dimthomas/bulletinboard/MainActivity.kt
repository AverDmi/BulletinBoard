package com.dimthomas.bulletinboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.dimthomas.bulletinboard.databinding.ActivityMainBinding
import com.dimthomas.bulletinboard.dialoghelper.DialogHelper
import com.dimthomas.bulletinboard.dialoghelper.DialogHelper.Companion.SIGN_IN_STATE
import com.dimthomas.bulletinboard.dialoghelper.DialogHelper.Companion.SIGN_UP_STATE
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    private lateinit var accountTv: TextView
    private lateinit var binding: ActivityMainBinding
    private val dialogHelper = DialogHelper(this)
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(mAuth.currentUser)
    }

    private fun init() {

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.mainContent.toolbar, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)

        accountTv = binding.navView.getHeaderView(0).findViewById(R.id.account_email_tv)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.my_ads -> {

            }
            R.id.cars -> {

            }
            R.id.pc -> {

            }
            R.id.smartphones -> {

            }
            R.id.sign_up -> {
                dialogHelper.createSignDialog(SIGN_UP_STATE)
            }
            R.id.sign_in-> {
                dialogHelper.createSignDialog(SIGN_IN_STATE)
            }
            R.id.sign_out -> {
                uiUpdate(null)
                mAuth.signOut()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun uiUpdate(user: FirebaseUser?) {
        accountTv.text = if (user == null) {
            resources.getString(R.string.not_auth)
        } else {
            user.email
        }
    }
}
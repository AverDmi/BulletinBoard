package com.dimthomas.bulletinboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.dimthomas.bulletinboard.databinding.ActivityMainBinding
import com.dimthomas.bulletinboard.dialoghelper.DialogHelper
import com.dimthomas.bulletinboard.dialoghelper.DialogHelper.Companion.SIGN_IN_STATE
import com.dimthomas.bulletinboard.dialoghelper.DialogHelper.Companion.SIGN_UP_STATE
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private val dialogHelper = DialogHelper(this)
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.mainContent.toolbar, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)
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

            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
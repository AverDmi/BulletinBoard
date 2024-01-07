package com.dimthomas.bulletinboard.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.dimthomas.bulletinboard.R
import com.dimthomas.bulletinboard.databinding.ActivityEditAdsBinding
import com.dimthomas.bulletinboard.dialogs.DialogSpinnerHelper
import com.dimthomas.bulletinboard.utils.CityHelper

class EditAdsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditAdsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listCountry = CityHelper.getAllCountries(this)
        val dialog = DialogSpinnerHelper()
        dialog.showSpinnerDialog(this, listCountry)

    }
}
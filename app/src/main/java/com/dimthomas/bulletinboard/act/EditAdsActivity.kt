package com.dimthomas.bulletinboard.act

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.dimthomas.bulletinboard.R
import com.dimthomas.bulletinboard.adapters.ImageAdapter
import com.dimthomas.bulletinboard.databinding.ActivityEditAdsBinding
import com.dimthomas.bulletinboard.dialogs.DialogSpinnerHelper
import com.dimthomas.bulletinboard.fragments.FragmentCloseInterface
import com.dimthomas.bulletinboard.fragments.ImageListFragment
import com.dimthomas.bulletinboard.utils.CityHelper
import com.dimthomas.bulletinboard.utils.ImageManager
import com.dimthomas.bulletinboard.utils.ImagePicker
import com.dimthomas.bulletinboard.utils.ImagePicker.REQUEST_CODE_GET_IMAGES
import com.dimthomas.bulletinboard.utils.ImagePicker.REQUEST_CODE_GET_SINGLE_IMAGE
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil

class EditAdsActivity : AppCompatActivity(), FragmentCloseInterface {

    private var chooseImageFragment: ImageListFragment? = null
    lateinit var binding: ActivityEditAdsBinding
    private val dialog = DialogSpinnerHelper()
    private lateinit var imageAdapter: ImageAdapter
    var editImagePosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_GET_IMAGES) {
            if (data != null) {
                val returnValues = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                if (returnValues?.size!! > 1 && chooseImageFragment == null) {

                    openChooseImageFragment(returnValues)

                } else if (returnValues.size == 1 && chooseImageFragment == null) {

//                    imageAdapter.update(returnValues)
                    val tempList = ImageManager.getImageSize(returnValues[0])


                } else if (chooseImageFragment != null) {

                    chooseImageFragment?.updateAdapter(returnValues)

                }
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_GET_SINGLE_IMAGE) {
            if (data != null) {
                val uris = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                chooseImageFragment?.setSingleImage(uris?.get(0)!!, editImagePosition)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ImagePicker.getImages(this, 3, REQUEST_CODE_GET_IMAGES)
                } else {

                    Toast.makeText(
                        this,
                        "Approve permissions to open Pix ImagePicker",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
    }

    private fun init() {
        imageAdapter = ImageAdapter()
        binding.imagesVp.adapter = imageAdapter
    }

    fun onClickSelectCountry(view: View) {
        val listCountry = CityHelper.getAllCountries(this)
        dialog.showSpinnerDialog(this, listCountry, binding.countryTv)
        if (binding.cityTv.text.toString() != getString(R.string.select_city)) {
            binding.cityTv.text = getString(R.string.select_city)
        }
    }

    fun onClickSelectCity(view: View) {
        val selectedCountry = binding.countryTv.text.toString()
        if (selectedCountry != getString(R.string.select_country)) {
            val listCity = CityHelper.getAllCities(selectedCountry, this)
            dialog.showSpinnerDialog(this, listCity, binding.cityTv)
        } else {
            Toast.makeText(this, "No country selected", Toast.LENGTH_SHORT).show()
        }
    }

    fun onClickGetImages(view: View) {

        if (imageAdapter.mainArray.size == 0) {
            ImagePicker.getImages(this, 3, REQUEST_CODE_GET_IMAGES)
        } else {
            openChooseImageFragment(null)
            chooseImageFragment?.updateAdapterFromEdit(imageAdapter.mainArray)
        }

    }

    override fun onFragmentClose(list: ArrayList<Bitmap>) {
        binding.mainScrollView.visibility = View.VISIBLE
        imageAdapter.update(list)
        chooseImageFragment = null
    }

    private fun openChooseImageFragment(newList: ArrayList<String>?) {
        chooseImageFragment = ImageListFragment(this, newList)
        binding.mainScrollView.visibility = View.GONE
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.place_holder, chooseImageFragment!!)
        fm.commit()
    }
}
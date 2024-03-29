package com.dimthomas.bulletinboard.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.exifinterface.media.ExifInterface
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import java.io.File

object ImageManager {

    private const val MAX_IMAGE_SIZE = 1000

    fun getImageSize(uri: String): List<Int> {

        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeFile(uri, options)

        return if (imageRotation(uri) == 90) {
            listOf(options.outHeight, options.outWidth)
        } else {
            listOf(options.outWidth, options.outHeight)
        }

    }

    private fun imageRotation(uri: String): Int {
        var rotation = 0

        val imageFile = File(uri)
        val exif = ExifInterface(imageFile.absolutePath)
        val orientation =
            exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        rotation =
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90 || orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                90
            } else {
                0
            }

        return rotation
    }

    suspend fun imageResize(uris: List<String>): List<Bitmap> = withContext(Dispatchers.IO) {

        val tempList = ArrayList<List<Int>>()
        val bitmapList = ArrayList<Bitmap>()

        for (n in uris.indices) {

            val size = getImageSize(uris[n])
            val imageRatio = size[0].toFloat() / size[1].toFloat()

            if (imageRatio > 1) {
                if (size[0] > MAX_IMAGE_SIZE) {
                    tempList.add(listOf(MAX_IMAGE_SIZE, (MAX_IMAGE_SIZE / imageRatio).toInt()))
                } else {
                    tempList.add(listOf(size[0], size[1]))
                }
            } else {
                if (size[1] > MAX_IMAGE_SIZE) {
                    tempList.add(listOf((MAX_IMAGE_SIZE * imageRatio).toInt(), MAX_IMAGE_SIZE))
                } else {
                    tempList.add(listOf(size[0], size[1]))
                }
            }
        }

        for (i in uris.indices) {
            bitmapList.add(
                Picasso.get().load(File(uris[i])).resize(tempList[i][0], tempList[i][1]).get()
            )
        }


        return@withContext bitmapList
    }
}
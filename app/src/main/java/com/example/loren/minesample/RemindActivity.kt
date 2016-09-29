package com.example.loren.minesample

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.text.TextUtils
import kotlinx.android.synthetic.main.remind_activity.*

/**
 *                Copyright (c) 16-9-26 by loren
 */
class RemindActivity : Activity() {

    val REQUEST_IMAGE_CAPTURE = 1
    val TAKE_PHOTO = 2
    lateinit var sharedPrefrences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.remind_activity)
        sharedPrefrences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
//        if (!TextUtils.isEmpty(sharedPrefrences.getString("bitmap", ""))) {
//            val byteExtra = sharedPrefrences.getString("bitmap", "").toByteArray()
//            image.setImageBitmap(BitmapFactory.decodeByteArray(byteExtra, 0, byteExtra.size))
//        }
        take_photo_tv.setOnClickListener { takePhotoIntent() }
        bg_take_photo_tv.setOnClickListener { startActivityForResult(Intent(this, TakePhotoActivity::class.java), TAKE_PHOTO) }
    }

    fun takePhotoIntent() {
        val takeIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takeIntent.resolveActivity(packageManager) != null)
            startActivityForResult(takeIntent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extra = data!!.extras
            val bitmap = extra.get("data") as Bitmap
            image.setImageBitmap(bitmap)
        }
        if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            val byteExtra = sharedPrefrences.getString("bitmap", "").toByteArray()
            image.setImageBitmap(BitmapFactory.decodeByteArray(byteExtra, 0, byteExtra.size))
        }
    }
}
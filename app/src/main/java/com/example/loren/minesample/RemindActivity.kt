package com.example.loren.minesample

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.remind_activity.*
import pers.victor.ext.toast

/**
 *                Copyright (c) 16-9-26 by loren
 */
class RemindActivity : BaseActivity() {
    override fun initWidgets() {
        sharedPrefrences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        take_photo_tv.setOnClickListener { requestPermission(Manifest.permission.CAMERA, granted = { takePhotoIntent() }, denied = { toast("没有权限，请授权后重试") }) }
        bg_take_photo_tv.setOnClickListener { requestPermission(Manifest.permission.CAMERA, granted = { startActivityForResult(Intent(this, TakePhotoActivity::class.java), TAKE_PHOTO) }, denied = { toast("没有权限，请授权后重试") }) }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.remind_activity

    val REQUEST_IMAGE_CAPTURE = 1
    val TAKE_PHOTO = 2
    lateinit var sharedPrefrences: SharedPreferences

    private fun takePhotoIntent() {
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
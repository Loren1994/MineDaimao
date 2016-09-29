package com.example.loren.minesample

import android.app.Activity
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.hardware.Camera
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.SurfaceHolder
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_take_photo.*
import java.io.*

class TakePhotoActivity : Activity() {

    private lateinit var mCamera: Camera
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var sharedPrefrence: SharedPreferences
    private lateinit var thread1: Thread
    private lateinit var thread2: Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_photo)
        init()
        mCamera.setPreviewDisplay(surfaceHolder)
        mCamera.startPreview()
        mCamera.autoFocus { b, camera -> }
        thread1 = Thread() {
            mCamera.takePicture(null, null,
                    Camera.PictureCallback { data, camera ->
                        thread2 = Thread {
//                            sharedPrefrence.edit().putString("bitmap", data.toString()).apply()
                            val bitmap = zoomImage(BitmapFactory.decodeByteArray(data, 0, data.size))
                            saveMyBitmap(bitmap, "无预览${System.currentTimeMillis()}")
                            runOnUiThread { Toast.makeText(this, "已保存到本地", Toast.LENGTH_SHORT).show() }
                        }
                        thread2.start()
                        finish()
                    })
        }
        thread1.start()
    }

    fun init() {
        surfaceHolder = surfaceview.holder
        mCamera = Camera.open(1)
        sharedPrefrence = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        mCamera.stopPreview()
        mCamera.release()
        thread1.interrupt()
        thread2.interrupt()
    }

    @Throws(IOException::class)
    fun saveMyBitmap(bmp: Bitmap, bitName: String): Boolean {
        val dirFile = File("./sdcard/DCIM/Camera/")
        if (!dirFile.exists()) {
            dirFile.mkdirs()
        }
        val f = File("./sdcard/DCIM/Camera/$bitName.png")
        var flag = false
        f.createNewFile()
        var fOut: FileOutputStream? = null
        try {
            fOut = FileOutputStream(f)

            bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut)

            flag = true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        try {
            fOut!!.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        try {
            fOut!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return flag
    }

    fun zoomImage(bgimage: Bitmap): Bitmap {
        val baos = ByteArrayOutputStream()
        val bitmap = rotationBitmap(bgimage)
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, baos)
//        val options = 10
//        while (baos.toByteArray().size / 1024 > 1024) {
//            baos.reset()
//            bgimage.compress(Bitmap.CompressFormat.PNG, options, baos)
//        }
        val isBm = ByteArrayInputStream(baos.toByteArray())
        val bm = BitmapFactory.decodeStream(isBm, null, null)
        return bm
    }

    fun rotationBitmap(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()

        val width = bitmap.width
        val height = bitmap.height
        val scale = bitmap.width / bitmap.height

        val newWidth = 480
        val newheight = newWidth / scale

        val widthScale = newWidth.toFloat() / width
        val heightScale = newheight.toFloat() / height

        matrix.postScale(widthScale, heightScale)
        matrix.preRotate(270f)
        val bm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
        return bm
    }
}

package com.example.loren.minesample

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.ImageReader
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Environment
import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.record_screen_activity.*
import pers.victor.ext.screenDensity
import pers.victor.ext.screenHeight
import pers.victor.ext.screenWidth
import java.io.File


/**
 * Copyright © 2018/4/4 by loren
 */
class RecordScreenActivity : BaseActivity() {

    private val JIE_PING = 0
    private val LU_PING = 1
    private lateinit var mediaProjectionManager: MediaProjectionManager
    private lateinit var virtualDisplay: VirtualDisplay
    private lateinit var mediaProjection: MediaProjection
    private var mediaRecorder = MediaRecorder()
    private var isStart = false

    override fun initWidgets() {
        mediaProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        record_btn.setOnClickListener {
            if (!isStart) {
                startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), LU_PING)
            } else {
                mediaRecorder.stop()
            }
            isStart = !isStart
            record_btn.text = if (isStart) "停止录屏" else "录屏"
        }
        capture_btn.setOnClickListener { startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), JIE_PING) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data)
        if (requestCode == LU_PING) {
            initRecorder()
            createVirtualDisplay()
            mediaRecorder.start()
        }
        if (requestCode == JIE_PING) {
            captureScreen()
        }
    }

    private fun initRecorder() {
        val file = File(Environment.getExternalStorageDirectory(), System.currentTimeMillis().toString() + ".mp4")
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setOutputFile(file.absolutePath)
        mediaRecorder.setVideoSize(screenWidth, screenHeight)
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder.setVideoEncodingBitRate(5 * 1024 * 1024)
        mediaRecorder.setVideoFrameRate(30)
        mediaRecorder.prepare()
    }

    private fun createVirtualDisplay() {
        virtualDisplay = mediaProjection.createVirtualDisplay("screen-mirror", screenWidth, screenHeight,
                screenDensity.toInt(), DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mediaRecorder.surface, null, null)
    }

    private fun captureScreen() {
        val imageReader = ImageReader.newInstance(screenWidth, screenHeight, PixelFormat.RGB_565, 1)
        virtualDisplay = mediaProjection.createVirtualDisplay("screen-mirror", screenWidth, screenHeight,
                screenDensity.toInt(), DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imageReader.surface, null, null)
        val image = imageReader.acquireLatestImage()
        if (image != null) {
            val width = image.width
            val height = image.height
            val planes = image.planes
            val buffer = planes[0].buffer
            val pixelStride = planes[0].pixelStride
            val rowStride = planes[0].rowStride
            val rowPadding = rowStride - pixelStride * width
            var bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888)
            bitmap.copyPixelsFromBuffer(buffer)
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height)
            image.close()
            result_iv.setImageBitmap(bitmap)
        }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.record_screen_activity
}
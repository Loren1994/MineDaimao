package com.example.loren.minesample

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.util.SparseIntArray
import android.view.Surface
import android.view.SurfaceHolder
import android.view.View
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_take_photo.*
import pers.victor.ext.findColor
import pers.victor.ext.toast
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.util.*

class TakePhotoActivity : BaseActivity() {
    override fun useTitleBar() = false

    override fun initData() {
        window.setBackgroundDrawable(ColorDrawable(findColor(R.color.transparent)))
    }

    override fun initWidgets() {
        ORIENTATIONS.append(Surface.ROTATION_0, 90)
        ORIENTATIONS.append(Surface.ROTATION_90, 0)
        ORIENTATIONS.append(Surface.ROTATION_180, 270)
        ORIENTATIONS.append(Surface.ROTATION_270, 180)
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, granted = { }, denied = { toast("没有权限，请授权后重试") })
        if (!this.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            toast("无前置摄像头")
            finish()
            return
        }
        initView()
        Thread {
            Thread.sleep(1000)
            takePicture()
        }.start()
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.activity_take_photo

    private var mSurfaceHolder: SurfaceHolder? = null
    private var mCameraManager: CameraManager? = null//摄像头管理器
    private var childHandler: Handler? = null
    private var mainHandler: Handler? = null
    private var mCameraID: String? = null//摄像头Id 0 为后  1 为前
    private var mImageReader: ImageReader? = null
    private var mCameraCaptureSession: CameraCaptureSession? = null
    private var mCameraDevice: CameraDevice? = null

    private val ORIENTATIONS: SparseIntArray = SparseIntArray()

    private fun initView() {
        surfaceview.setOnClickListener(this)
        mSurfaceHolder = surfaceview.holder
        mSurfaceHolder!!.setKeepScreenOn(false)
        surfaceview.setZOrderOnTop(false)
        mSurfaceHolder!!.setFormat(PixelFormat.TRANSLUCENT)
        mSurfaceHolder!!.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
            }

            override fun surfaceDestroyed(p0: SurfaceHolder?) {
                // 释放Camera资源
                if (null != mCameraDevice) {
                    mCameraDevice!!.close()
                    mCameraDevice = null
                }
            }

            override fun surfaceCreated(p0: SurfaceHolder?) {
                // 初始化Camera
                initCamera2()
            }

        })
    }

    private fun takePicture() {
        if (mCameraDevice == null) return
        // 创建拍照需要的CaptureRequest.Builder
        val captureRequestBuilder: CaptureRequest.Builder = mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
        // 将imageReader的surface作为CaptureRequest.Builder的目标
        captureRequestBuilder.addTarget(mImageReader!!.surface)
        // 自动对焦
        captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
        // 自动曝光
        captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
        // 获取手机方向
        val rotation: Int = windowManager.defaultDisplay.rotation
        // 根据设备方向计算设置照片的方向
        captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation))
        //拍照
        val mCaptureRequest: CaptureRequest = captureRequestBuilder.build()
        mCameraCaptureSession!!.capture(mCaptureRequest, null, childHandler)
    }

    /**
     * 初始化Camera2
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun initCamera2() {
        val handlerThread = HandlerThread("Camera2")
        handlerThread.start()
        childHandler = Handler(handlerThread.looper)
        mainHandler = Handler(mainLooper)
//        mCameraID = "" + CameraCharacteristics.LENS_FACING_FRONT//后摄像头
        mCameraID = "" + CameraCharacteristics.LENS_FACING_BACK//前摄像头
        mImageReader = ImageReader.newInstance(1080, 1920, ImageFormat.JPEG, 1)
        mImageReader!!.setOnImageAvailableListener({ p0 ->
            //可以在这里处理拍照得到的临时照片 例如，写入本地
            mCameraDevice!!.close()
            // 拿到拍照照片数据
            val image: Image = p0!!.acquireNextImage()
            val buffer: ByteBuffer = image.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)//由缓冲区存入字节数组
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            if (bitmap != null) {
                saveMyBitmap(bitmap, "无预览${System.currentTimeMillis()}")
            }
        }, mainHandler)
        //获取摄像头管理
        mCameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        //打开摄像头
        mCameraManager!!.openCamera(mCameraID, stateCallback, mainHandler)
    }


    /**
     * 摄像头创建监听
     */
    private val stateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(p0: CameraDevice?) {
            //打开摄像头
            mCameraDevice = p0
            //开启预览
            takePreview()
        }

        override fun onDisconnected(p0: CameraDevice?) {
            //关闭摄像头
            if (null != mCameraDevice) {
                mCameraDevice!!.close()
                mCameraDevice = null
            }
        }

        override fun onError(p0: CameraDevice?, p1: Int) {
            Log.d("", "发生错误")
        }

    }

    /**
     * 开始预览
     */
    private fun takePreview() {
        // 创建预览需要的CaptureRequest.Builder
        val previewRequestBuilder: CaptureRequest.Builder = mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        // 将SurfaceView的surface作为CaptureRequest.Builder的目标
        previewRequestBuilder.addTarget(mSurfaceHolder!!.surface)
        // 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
        mCameraDevice!!.createCaptureSession(Arrays.asList(mSurfaceHolder!!.surface, mImageReader!!.surface), object : CameraCaptureSession.StateCallback() {
            override fun onConfigureFailed(p0: CameraCaptureSession?) {
                log("配置失败")
            }

            override fun onConfigured(p0: CameraCaptureSession?) {
                if (null == mCameraDevice) return
                // 当摄像头已经准备好时，开始显示预览
                mCameraCaptureSession = p0
                // 自动对焦
                previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                // 打开闪光灯
                previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
                // 显示预览(此处无预览拍照)
                //val previewRequest = previewRequestBuilder.build()
                //mCameraCaptureSession!!.setRepeatingRequest(previewRequest, null, childHandler)
            }
        }, childHandler)
    }

    override fun onDestroy() {
        //FIXME 无预览拍照后,预览拍照不好用
        super.onDestroy()
    }

    @Throws(IOException::class)
    private fun saveMyBitmap(bmp: Bitmap, bitName: String): Boolean {
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
        toast("保存成功${dirFile.path}")
        val intent = Intent()
        val bundle = Bundle()
        bundle.putParcelable("data", bmp)
        intent.putExtra("bundle", bundle)
        this.setResult(Activity.RESULT_OK, intent)
        finish()
        return flag
    }

}

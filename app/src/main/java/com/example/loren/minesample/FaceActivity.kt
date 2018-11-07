package com.example.loren.minesample

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.view.View
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseActivity
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequestBuilder
import kotlinx.android.synthetic.main.face_activity.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_imgcodecs.imread
import org.bytedeco.javacpp.opencv_imgcodecs.imwrite
import org.bytedeco.javacpp.opencv_imgproc.*
import org.bytedeco.javacpp.opencv_objdetect
import pers.victor.ext.saveFile
import pers.victor.ext.toast
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


/**
 * OpenCV主要实现人脸检测功能
 * JavaCV主要实现人脸对比功能
 *
 * Copyright © 2018/6/5 by loren
 */
class FaceActivity : BaseActivity() {

    private val url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528288913857&di=96dc6d18d33441707e9c929016549a7b&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F78310a55b319ebc4e741e1048826cffc1e171675.jpg"
    private var startTime = 0L
    private val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())

    override fun initWidgets() {
        know_btn.setOnClickListener {
            val picPath = "${Environment.getExternalStorageDirectory()}/home.png"
            startTime = System.currentTimeMillis()
            launch {
                async { faceDetect(picPath) }.await()
            }
        }
        down_btn.setOnClickListener { downloadPic() }
    }


    private fun faceDetect(imageFilePath: String) {
        val image = imread(imageFilePath)
        val grayImage = opencv_core.Mat()
        cvtColor(image, grayImage, COLOR_BGR2GRAY) // 变成灰度图像
        equalizeHist(grayImage, grayImage) // 直方图均衡，增加对比度以提高识别率
        // 配置检测
        val faceDetector = getCascade()
        val faceDetections = opencv_core.RectVector()
        faceDetector.detectMultiScale(grayImage, faceDetections)
        log("识别出${faceDetections.size()}张脸\n识别耗时:${(System.currentTimeMillis() - startTime) / 1000.00}s")
        val file = File("${Environment.getExternalStorageDirectory()}/face")
        if (!file.exists()) {
            file.mkdirs()
        }

        val dirPath = file.absolutePath
        for (i in 0 until faceDetections.size()) {
            val rect = faceDetections.get(i)
            saveFace(image, rect, "$dirPath/face-$i-${sdf.format(Date())}.png")
            //标记人脸
            val point = opencv_core.Point(rect.x(), rect.y())
            val point1 = opencv_core.Point(rect.x() + rect.width(), rect.y() + rect.height())
            rectangle(image, point, point1, opencv_core.Scalar(0.0, 255.0, 0.0, 0.0))
        }
//        保存标记人脸后的图像
        val newFile = "$dirPath/faces.png"
        log("写入:$newFile")
        imwrite(newFile, image)
        log("总共耗时:${(System.currentTimeMillis() - startTime) / 1000.00}s")
        runOnUiThread { toast("识别完成-存储至${Environment.getExternalStorageDirectory()}/face文件夹") }
    }

    private fun saveFace(image: opencv_core.Mat, rect: opencv_core.Rect, filePath: String): Boolean {
        val sub = image.rowRange(rect.y(), rect.y() + rect.height())
                .colRange(rect.x(), rect.x() + rect.width())
        val mat = opencv_core.Mat()
        val size = opencv_core.Size(100, 100)
        resize(sub, mat, size)
        log("保存人脸:$filePath")
        return imwrite(filePath, mat)
    }

    private fun getCascade(): opencv_objdetect.CascadeClassifier {
        val `is` = resources.openRawResource(R.raw.lbpcascade_frontalface)
        val cascadeDir = getDir("cascade", Context.MODE_PRIVATE)
        val mCascadeFile = File(cascadeDir, "cascade.xml")
        val os = FileOutputStream(mCascadeFile)
        val buffer = ByteArray(4096)
        var bytesRead = 0
        fun readBuffer(): Int {
            bytesRead = `is`.read(buffer)
            return bytesRead
        }
        while (readBuffer() != -1) {
            os.write(buffer, 0, bytesRead)
        }
        `is`.close()
        os.close()
        val cascadeClassifier = opencv_objdetect.CascadeClassifier(mCascadeFile.absolutePath)
        cascadeClassifier.load(mCascadeFile.absolutePath)
        return cascadeClassifier
    }

    private fun downloadPic() {
        val imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setProgressiveRenderingEnabled(true).build()
        val dataSource = Fresco.getImagePipeline().fetchDecodedImage(imageRequest, this)
        dataSource.subscribe(object : BaseBitmapDataSubscriber() {
            override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>?) {

            }

            public override fun onNewResultImpl(bitmap: Bitmap?) {
                bitmap?.saveFile("${Environment.getExternalStorageDirectory()}/home.png")
                runOnUiThread { toast("保存至${Environment.getExternalStorageDirectory()}/home.png") }
            }
        }, CallerThreadExecutor.getInstance())
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.face_activity
}
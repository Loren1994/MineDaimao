package com.example.loren.minesample

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.WindowManager
import com.example.loren.minesample.adapter.BlurListAdapter
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.blur_list_activity.*


/**
 * Copyright © 03/01/2018 by loren
 */
class BlurListActivity : BaseActivity() {

    private val BITMAP_SCALE = 0.4f
    private val BLUR_RADIUS = 25f
    private var mScrollY = 0
    private var mAlpha = 0
    private var data: MutableList<String> = arrayListOf()
    private lateinit var mAdapter: BlurListAdapter

    override fun initWidgets() {
        allowFullScreen()
        val originBmp = BitmapFactory.decodeResource(resources, R.drawable.victor_chou)
        val blurBmp = blurBitmap(this, originBmp)
        blur_iv.setImageBitmap(blurBmp)
        origin_iv.setImageBitmap(originBmp)
        repeat(20) {
            data.add("ITEM - $it")
        }
        mAdapter = BlurListAdapter(data)
        list_rv.adapter = mAdapter
        list_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mScrollY += dy
                mAlpha = if (mScrollY <= 1000) {
                    (255 - (mScrollY / 10) * 2.55).toInt()
                } else {
                    0
                }
                origin_iv.imageAlpha = mAlpha
            }
        })
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    private fun blurBitmap(context: Context, image: Bitmap): Bitmap {
        // 将图片缩小比例,渲染的效率更高
        val width = Math.round(image.width * BITMAP_SCALE)
        val height = Math.round(image.height * BITMAP_SCALE)
        // 将缩小后的图片做为预渲染的图片
        val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
        // 创建一张渲染后的输出图片
        val outputBitmap = Bitmap.createBitmap(inputBitmap)
        // 创建RenderScript内核对象
        val rs = RenderScript.create(context)
        // 创建一个模糊效果的RenderScript的工具对象
        val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(BLUR_RADIUS)
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn)
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut)
        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap)
        return outputBitmap
    }

    override fun useTitleBar() = false

    override fun bindLayout() = R.layout.blur_list_activity
}
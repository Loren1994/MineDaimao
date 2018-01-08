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
import com.example.loren.minesample.adapter.BlurListAdapter
import com.example.loren.minesample.base.ext.parseObject
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.entity.WeatherBean
import com.example.loren.minesample.util.Constant
import com.example.loren.minesample.util.http
import kotlinx.android.synthetic.main.blur_list_activity.*
import pers.victor.ext.*


/**
 * Copyright © 03/01/2018 by loren
 */
class BlurListActivity : BaseActivity() {

    private val IMAGE_OFFSET = 100
    private val OFFSET_MULTIPLE = 8
    private val BITMAP_SCALE = 0.4f
    private val BLUR_RADIUS = 25f
    private var mScrollY = 0
    private var mAlpha = 0
    private var data: MutableList<WeatherBean.Data.Forecast> = arrayListOf()
    private lateinit var mAdapter: BlurListAdapter
    private val CITY_NAME = "青岛"
    private val GET_WEATHER_URL
        get() = "http://wthrcdn.etouch.cn/weather_mini?city=$CITY_NAME"
    private lateinit var weatherBean: WeatherBean

    override fun initWidgets() {
        //加此flag,全屏包括虚拟键部分
        //window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        weather_toolbar.post {
            val param = weather_toolbar.layoutParams
            param.height = weather_toolbar.measuredHeight + getStatusBarHeight()
            weather_toolbar.layoutParams = param
        }
        mAdapter = BlurListAdapter(data)
        list_rv.adapter = mAdapter
        initImage()
        getWeather()
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
                if (mScrollY <= IMAGE_OFFSET * OFFSET_MULTIPLE) {
                    setTop(-mScrollY / OFFSET_MULTIPLE)
                }
            }
        })
    }

    private fun setTop(y: Int) {
        origin_iv.top = y
        blur_iv.top = y
    }

    private fun getWeather() {
        showLoadingDialog()
        city_name.text = CITY_NAME
        time_tv.text = System.currentTimeMillis().dateOnly()
        http {
            url = GET_WEATHER_URL
            success {
                dismissLoadingDialog()
                weatherBean = parseObject(it)
                setData()
            }
            fail {
                toast(it)
                dismissLoadingDialog()
                weatherBean = parseObject(Constant.WEATHER_JSON)
                setData()
            }
        }
    }

    private fun setData() {
        val header = weatherBean.data.forecast[0].copy()
        header.date = weatherBean.data.ganmao
        header.high = weatherBean.data.wendu
        data.add(header)
        weatherBean.data.forecast.forEach { data.add(it) }
        mAdapter.notifyDataSetChanged()
    }

    //根布局必须为FrameLayout,否则图片超出屏幕部分为空白
    private fun initImage() {
        val originBmp = BitmapFactory.decodeResource(resources, R.drawable.wangye)
        val blurBmp = blurBitmap(this, originBmp)
        blur_iv.setImageBitmap(blurBmp)
        origin_iv.setImageBitmap(originBmp)
        val originParam = origin_iv.layoutParams
        val blurParam = blur_iv.layoutParams
        originParam.height = screenHeight + IMAGE_OFFSET + getVirNavBarHeight()
        blurParam.height = screenHeight + IMAGE_OFFSET + getVirNavBarHeight()
        origin_iv.layoutParams = originParam
        blur_iv.layoutParams = blurParam
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

//    override fun allowFullScreen() = false

    override fun useImmersive() = true

    override fun bindLayout() = R.layout.blur_list_activity
}
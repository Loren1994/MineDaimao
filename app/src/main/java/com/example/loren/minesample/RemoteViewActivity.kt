package com.example.loren.minesample

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.graphics.pdf.PdfRenderer
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.remote_view_activity.*
import pers.victor.ext.*
import java.io.File
import java.io.FileOutputStream


/**
 * Copyright © 20/12/2017 by loren
 */
class RemoteViewActivity : BaseActivity() {

    private lateinit var folder: String
    private var clickPos = 0
    private lateinit var clickFile: File
    private var render: PdfRenderer? = null
    private lateinit var dialog: AlertDialog.Builder
    private lateinit var files: Array<File>
    private var pageBitmap: Bitmap? = null

    override fun initWidgets() {
        folder = "${Environment.getExternalStorageDirectory()}/createPDF"
        val file = File(folder)
        if (!file.exists()) {
            file.mkdirs()
        }
        dialog = AlertDialog.Builder(this).setTitle("选择您创建过得PDF文件")
        create_pdf_btn.setOnClickListener { createPDF() }
        look_pdf_btn.setOnClickListener { chooseFile() }
    }

    private fun chooseFile() {
        files = File(folder).listFiles()
        if (files.isEmpty()) {
            toast("没有文件")
            return
        }
        val filesName = arrayOfNulls<String>(files.size)
        files.forEachIndexed { index, item -> filesName[index] = item.name }
        dialog.setSingleChoiceItems(filesName, 0) { _, pos ->
            clickPos = pos
        }.setPositiveButton("好", { _, _ ->
            clickFile = files.filterIndexed { index, _ -> index == clickPos }[0]
            lookPDF()
        })
        dialog.show()
    }

    private fun lookPDF() {
        val parcelFile = ParcelFileDescriptor.open(clickFile, ParcelFileDescriptor.MODE_READ_ONLY)
        render = PdfRenderer(parcelFile)
        createPageNum(render!!.pageCount)
        renderPage(0)
    }

    private fun renderPage(curPage: Int) {
        showLoadingDialog()
        val page = render!!.openPage(curPage)
        pageBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)
        page.render(pageBitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        page.close()
        pdf_looker_iv.setImageBitmap(pageBitmap)
        dismissLoadingDialog()
    }

    override fun onDestroy() {
        render?.close()
        pageBitmap?.recycle()
        super.onDestroy()
    }

    private fun createPageNum(num: Int) {
        num_container_ll.removeAllViews()
        repeat(num) { index ->
            val view = TextView(this)
            view.text = (index + 1).toString()
            view.setPadding(dp2px(10), 0, dp2px(10), 0)
            view.background = findDrawable(R.drawable.bg_white_coner)
            val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            param.setMargins(dp2px(5), 0, 0, 0)
            view.layoutParams = param
            view.setOnClickListener {
                toast("第${index + 1}页")
                renderPage(index)
            }
            num_container_ll.addView(view)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun createPDF() {
        val pdf = PdfDocument()
        page_num_tv.visibility = View.VISIBLE
        repeat(3) {
            page_num_tv.text = "第${it + 1}页"
            val colorId = when (it) {
                0 -> R.color.green
                1 -> R.color.red
                2 -> R.color.yellow
                else -> R.color.background_grey
            }
            bottom_parent_rl.background = findDrawable(colorId)
            val pageInfo = PageInfo.Builder(screenWidth, screenHeight, it).create()
            val page = pdf.startPage(pageInfo)
            val contentView = window.decorView
            contentView.draw(page.canvas)
            pdf.finishPage(page)
        }
        val name = "${System.currentTimeMillis()}.pdf"
        pdf.writeTo(FileOutputStream("$folder/$name"))
        page_num_tv.visibility = View.INVISIBLE
        bottom_parent_rl.background = findDrawable(R.color.background_grey)
        toast("创建成功 - $folder/$name")
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.remote_view_activity
}
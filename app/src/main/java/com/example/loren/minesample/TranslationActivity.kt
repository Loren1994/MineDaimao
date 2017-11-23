package com.example.loren.minesample

import android.view.View
import bean.TranslationBean
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.util.http
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_translation.*
import pers.victor.ext.toast

class TranslationActivity : BaseActivity() {
    override fun initWidgets() {
        translation_iv!!.setOnClickListener { toTranslation() }
        container_ll!!.getChildAt(0).setOnClickListener { translate() }

    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.activity_translation

    private val TRANSLATION_URL = "http://fanyi.youdao.com/openapi.do?keyfrom=Skykai521&key=977124034&type=data&doctype=json&version=1.1&q="

    private fun translate() {

    }


    private fun toTranslation() {
        http {
            url = TRANSLATION_URL + input_edt!!.text.toString().trim { it <= ' ' }
            success {
                try {
                    val bean = Gson().fromJson(it, TranslationBean::class.java)
                    var result = ""
                    for (i in 0 until bean.basic.explains.size) {
                        result += bean.basic.explains[i] + "; "
                    }
                    output_edt!!.setText(result)
                } catch (e: Exception) {
                    e.printStackTrace()
                    toast("异常了哈哈哈哈哈")
                }
            }
            fail {
                toast(it)
            }
        }
    }
}

package com.example.loren.minesample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import bean.TranslationBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_translation.*
import util.HttpUtil

class TranslationActivity : AppCompatActivity() {

    private val TRANSLATION_URL = "http://fanyi.youdao.com/openapi.do?keyfrom=Skykai521&key=977124034&type=data&doctype=json&version=1.1&q="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation)
        translation_iv!!.setOnClickListener { toTranslation() }
        container_ll!!.getChildAt(0).setOnClickListener { translate() }
    }

    private fun translate() {

    }


    private fun toTranslation() {
        HttpUtil.get(TRANSLATION_URL + input_edt!!.text.toString().trim { it <= ' ' }, object : HttpUtil.NetCallBack {
            override fun onSuccess(json: String) {
                try {
                    val bean = Gson().fromJson(json, TranslationBean::class.java)
                    var result = ""
                    for (i in 0 until bean.basic.explains.size) {
                        result += bean.basic.explains[i] + "  "
                    }
                    output_edt!!.setText(result)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@TranslationActivity, "异常了哈哈哈哈哈", Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(reason: String) {
                Toast.makeText(this@TranslationActivity, reason, Toast.LENGTH_LONG).show()
            }
        })
    }
}

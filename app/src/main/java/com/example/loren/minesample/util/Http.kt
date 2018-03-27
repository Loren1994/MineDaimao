package com.example.loren.minesample.util

import android.os.Handler
import android.os.Looper
import com.example.loren.minesample.base.ext.err
import com.example.loren.minesample.base.ext.json
import com.example.loren.minesample.base.ext.log
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by Victor on 2017/6/16. (ง •̀_•́)ง
 */

private val client = OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).build()
private val JSON = MediaType.parse("application/json; charset=utf-8")
private val handler = Handler(Looper.getMainLooper())
private val ALI_APPCODE = "72a0cf3506bf4cff90e0470cb152e51f"

class Http {

    lateinit var url: String
    var body: JSONObject? = null
    internal var success: ((String?) -> Unit)? = null
    internal var fail: ((String?) -> Unit)? = null

    fun success(block: (json: String?) -> Unit) {
        success = block
    }

    fun fail(block: (reason: String?) -> Unit) {
        fail = block
    }
}

fun http(create: Http.() -> Unit) {
    val h = Http().apply { create() }
    if (h.body == null) httpGet(h) else httpPost(h)
}

private fun httpGet(http: Http) {
    log("get: " + http.url)
    val request = Request.Builder()
            .url(http.url)
            .addHeader("Authorization", "APPCODE $ALI_APPCODE")
            .build()
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            if (e.message != null && !e.message!!.contentEquals("Canceled"))
                httpFailure(e, http)
        }

        @Throws(IOException::class)
        override fun onResponse(call: Call, response: Response) {
            httpResponse(response, http)
        }
    })
}

private fun httpPost(http: Http) {
    val jo = http.body!!
    json(jo.toString())
    log("post: " + http.url)
    val requestBody = RequestBody.create(JSON, jo.toString())
    val request = Request.Builder()
            .url(http.url)
            .post(requestBody)
            .build()
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            if (e.message != null && !e.message!!.contentEquals("Canceled"))
                httpFailure(e, http)
        }

        @Throws(IOException::class)
        override fun onResponse(call: Call, response: Response) {
            httpResponse(response, http)
        }
    })
}

private fun httpFailure(e: IOException, http: Http) {
    var msg = e.message ?: "网络错误"
    if ("No address associated with hostname" in msg) {
        msg = "网络未连接"
    }
    if ("failed to connect to" in msg || "timeout" in msg) {
        msg = "网络连接超时"
    }
    handler.post { http.fail?.invoke(msg) }
}

private fun httpResponse(response: Response, http: Http) {
    val result = response.body()?.string() ?: ""
    json(result)
    val jo: JSONObject
    try {
        jo = JSONObject(result)
    } catch (e: JSONException) {
        handler.post { http.fail?.invoke("返回JSON格式异常") }
        return
    }
    if (jo.has("status") && jo.has("result")) {
        when {
            (jo.getString("status"))!!.contentEquals("0") ->
                handler.post { http.success?.invoke(jo.getJSONObject("result").getString("content")) }
            else -> handler.post { http.fail?.invoke("请求失败") }
        }
    } else if (jo.has("basic")) {
        when {
            (jo.getString("errorCode"))!!.contentEquals("0") ->
                handler.post { http.success?.invoke(jo.toString()) }
            else -> handler.post { http.fail?.invoke("请求失败") }
        }
    } else if (jo.has("data")) { //天气接口
        handler.post { http.success?.invoke(jo.toString()) }
    } else {
        err(result)
        handler.post { http.fail?.invoke("无result") }
    }
}
package com.example.loren.minesample.server

import android.content.pm.ApplicationInfo
import com.example.loren.minesample.App
import com.example.loren.minesample.entity.AppBean
import com.google.gson.Gson
import com.yanzhenjie.andserver.RequestHandler
import com.yanzhenjie.andserver.RequestMethod
import com.yanzhenjie.andserver.annotation.RequestMapping
import org.apache.httpcore.HttpRequest
import org.apache.httpcore.HttpResponse
import org.apache.httpcore.entity.StringEntity
import org.apache.httpcore.protocol.HttpContext
import java.io.File
import java.math.BigDecimal
import java.util.*


/**
 * Copyright © 22/01/2018 by loren
 */
class AppHandler : RequestHandler {

    @RequestMapping(method = [RequestMethod.GET])
    override fun handle(request: HttpRequest, response: HttpResponse, context: HttpContext) {
        val result = Gson().toJson(getAppList())
        val strEntity = StringEntity(result, "utf-8")
        response.setStatusCode(200)
        response.entity = strEntity
    }

    private fun getAppList(): MutableList<AppBean> {
        val data: ArrayList<AppBean> = arrayListOf()
        val pm = App.application.packageManager
        val appList = pm.getInstalledPackages(0)
        appList.forEach {
            if (it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != ApplicationInfo.FLAG_SYSTEM) {
                val appBean = AppBean()
                appBean.lastUpdateTime = it.lastUpdateTime
                appBean.applicationInfo = it.applicationInfo
                appBean.firstInstallTime = it.firstInstallTime
                appBean.packageName = it.packageName
                appBean.pid = it.applicationInfo.loadLabel(pm).toString()
                appBean.sourceDir = it.applicationInfo.sourceDir
                appBean.apkSize = parseApkSize(File(it.applicationInfo.sourceDir).length()).toString()
                data.add(appBean)
            }
        }
        return data
    }

    private fun parseApkSize(size: Long): BigDecimal {
        val bd = BigDecimal(size / (1024.0 * 1024.0))
        return bd.setScale(2, BigDecimal.ROUND_DOWN)
    }
}
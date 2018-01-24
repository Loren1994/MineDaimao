package com.example.loren.minesample.server

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.example.loren.minesample.App
import com.example.loren.minesample.base.ext.log
import com.yanzhenjie.andserver.RequestHandler
import com.yanzhenjie.andserver.RequestMethod
import com.yanzhenjie.andserver.annotation.RequestMapping
import com.yanzhenjie.andserver.util.HttpRequestParser
import org.apache.httpcore.HttpRequest
import org.apache.httpcore.HttpResponse
import org.apache.httpcore.entity.StringEntity
import org.apache.httpcore.protocol.HttpContext
import java.net.URLDecoder

/**
 * Copyright © 24/01/2018 by loren
 */
class OpenApkHandler : RequestHandler {

    @RequestMapping(method = [RequestMethod.POST])
    override fun handle(request: HttpRequest, response: HttpResponse, context: HttpContext) {
        val params = HttpRequestParser.parseParams(request)
        log(params)
        if (!params.containsKey("pkName")) {
            response.setStatusCode(400)
            response.entity = StringEntity("缺少参数 - pkName", "utf-8")
            return
        }
        val pkName = URLDecoder.decode(params["pkName"], "utf-8")
        openApk(pkName)
        response.setStatusCode(200)
        response.entity = StringEntity("操作成功", "utf-8")
    }

    private fun openApk(packageName: String) {
//        val pm = App.application.packageManager
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = Uri.fromParts("package", packageName, null)
        App.application.startActivity(intent)
    }
}
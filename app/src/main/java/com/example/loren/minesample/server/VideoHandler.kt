package com.example.loren.minesample.server

import com.yanzhenjie.andserver.RequestHandler
import com.yanzhenjie.andserver.RequestMethod
import com.yanzhenjie.andserver.annotation.RequestMapping
import com.yanzhenjie.andserver.view.OkView
import com.yanzhenjie.andserver.view.View
import org.apache.httpcore.HttpRequest
import org.apache.httpcore.HttpResponse
import org.apache.httpcore.protocol.HttpContext


/**
 * Copyright © 22/01/2018 by loren
 */
class VideoHandler : RequestHandler {

    @RequestMapping(method = [RequestMethod.GET])
    override fun handle(request: HttpRequest, response: HttpResponse, context: HttpContext) {
        val view = handle(request, response)
        response.setStatusCode(view.httpCode)
        response.entity = view.httpEntity
        response.setHeaders(view.headers)
    }

    private fun handle(request: HttpRequest, response: HttpResponse): View {
        return OkView("loren-test")
    }

}
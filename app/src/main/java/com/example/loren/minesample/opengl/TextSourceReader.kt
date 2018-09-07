package com.example.loren.minesample.opengl

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Copyright © 2018/9/7 by loren
 */
object TextSourceReader {
    fun readTextFileFromSource(context: Context, resourceId: Int): String {
        val stringBuilder = StringBuilder()
        val inputStream = context.resources.openRawResource(resourceId)
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var nextLineStr: String? = null
        fun read(): Boolean {
            nextLineStr = bufferedReader.readLine()
            return nextLineStr != null
        }
        while (read()) {
            stringBuilder.append(nextLineStr)
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }
}
package com.example.loren.minesample.opengl

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLUtils
import com.example.loren.minesample.base.ext.log

/**
 * Copyright © 2018/9/10 by loren
 */
object TextureHelper {
    fun loadTexture(context: Context, resourceId: Int): Int {
        val textureObjId = IntArray(1)
        GLES20.glGenTextures(1, textureObjId, 0)
        if (textureObjId[0] == 0) {
            log("不能生成新纹理")
            return 0
        }
        val option = BitmapFactory.Options()
        option.inScaled = false
        val bitmap = BitmapFactory.decodeResource(context.resources, resourceId, option)
        if (null == bitmap) {
            log("$resourceId can not decode bitmap")
            GLES20.glDeleteTextures(1, textureObjId, 0)
            return 0
        }
        //告诉OpenGL后面纹理调用应该是应用于哪个纹理对象
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjId[0])
        //设置缩小的时候（GL_TEXTURE_MIN_FILTER）使用mipmap三线程过滤
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR)
        //设置放大的时候（GL_TEXTURE_MAG_FILTER）使用双线程过滤
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
        bitmap.recycle()
        //快速生成mipmap贴图
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D)
        //解除纹理操作的绑定
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
        return textureObjId[0]
    }
}
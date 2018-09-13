package com.example.loren.minesample.opengl

import android.opengl.GLES20
import android.opengl.GLES20.GL_FLOAT
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.opengl.Constant.BYTE_PER_FLOAT
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Copyright © 2018/9/10 by loren
 */
class VertexArray(vertexData: FloatArray) {
    private var floatBuffer = ByteBuffer
            .allocateDirect(vertexData.size * BYTE_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(vertexData)

    fun setVertexAttributePoint(offset: Int, attributeLocation: Int, componentCount: Int, stride: Int) {
        floatBuffer.position(offset)
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT, false, stride, floatBuffer)
        GLES20.glEnableVertexAttribArray(attributeLocation)
        floatBuffer.position(0)
    }
}
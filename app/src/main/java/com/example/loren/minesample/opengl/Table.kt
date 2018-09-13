package com.example.loren.minesample.opengl

import android.opengl.GLES20
import com.example.loren.minesample.opengl.Constant.BYTE_PER_FLOAT

/**
 * Copyright © 2018/9/10 by loren
 */
class Table {
    private var VERTEX_DATA = floatArrayOf(
            //x,y,s,t
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,
            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f
    )
    private var POSITION_COMPONENT_COUNT = 2
    private var TEXTURE_COORDINATES_COMPONENT_COUNT = 2
    private var STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTE_PER_FLOAT
    private var vertexArray = VertexArray(VERTEX_DATA)

    fun bindData(shaderProgram: TextureShaderProgram) {
        vertexArray.setVertexAttributePoint(
                0,
                shaderProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT,
                STRIDE)
        vertexArray.setVertexAttributePoint(
                POSITION_COMPONENT_COUNT,
                shaderProgram.aTextureCoordinatesLocation,
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE)
    }

    fun draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6)
    }

}
package com.example.loren.minesample.opengl

import android.opengl.GLES20
import com.example.loren.minesample.opengl.Constant.BYTE_PER_FLOAT

/**
 * Copyright © 2018/9/12 by loren
 */
class Mallet {
    private var POSITION_COMPONENT_COUNT = 2
    private var COLOR_COMPONENT_COUNT = 3
    private var STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTE_PER_FLOAT
    private var VERTEX_DATA = floatArrayOf(
            //x,y,r,g,b
            0f, -0.4f, 0f, 0f, 1f,
            0f, 0.4f, 1f, 0f, 0f
    )
    private val vertexArray = VertexArray(VERTEX_DATA)

    fun bindData(shaderProgram: ColorShaderProgram) {
        vertexArray.setVertexAttributePoint(
                0,
                shaderProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT,
                STRIDE
        )
        vertexArray.setVertexAttributePoint(
                POSITION_COMPONENT_COUNT,
                shaderProgram.aColorLocation,
                COLOR_COMPONENT_COUNT,
                STRIDE
        )
    }

    fun draw() {
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 2)
    }

}
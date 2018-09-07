package com.example.loren.minesample.opengl

import android.opengl.GLES20
import com.example.loren.minesample.base.ext.log

/**
 * Copyright © 2018/9/7 by loren
 */
object ShaderHelper {
    /*编译顶点着色器*/
    fun compileVertexSahder(shapeCode: String): Int {
        return compileShader(GLES20.GL_VERTEX_SHADER, shapeCode)
    }

    /*编译片段着色器*/
    fun compileFragmentShader(shapeCode: String): Int {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shapeCode)
    }

    private fun compileShader(type: Int, code: String): Int {
        val shaderObjectId = GLES20.glCreateShader(type)
        if (shaderObjectId == 0) {
            log("不能创建新的着色器")
            return 0
        }
        GLES20.glShaderSource(shaderObjectId, code)
        GLES20.glCompileShader(shaderObjectId)
        val compileStatus = IntArray(1)
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0)
        log("编译结果为:${GLES20.glGetShaderInfoLog(shaderObjectId)}")
        if (compileStatus[0] == 0) {
            log("编译着色器失败")
            GLES20.glDeleteShader(shaderObjectId)
            return 0
        }
        return shaderObjectId
    }

    /*连接顶点和片段着色器*/
    fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        val programId = GLES20.glCreateProgram()
        if (programId == 0) {
            log("could not create gl program")
            return 0
        }
        GLES20.glAttachShader(programId, vertexShaderId)
        GLES20.glAttachShader(programId, fragmentShaderId)
        GLES20.glLinkProgram(programId)
        log("program info: ${GLES20.glGetProgramInfoLog(programId)}")
        val status = IntArray(1)
        GLES20.glGetProgramiv(programId, GLES20.GL_LINK_STATUS, status, 0)
        if (status[0] == 0) {
            GLES20.glDeleteProgram(programId)
            log("link gl program fail")
            return 0
        }
        return programId
    }

    /*检测program*/
    fun validateProgram(programId: Int): Boolean {
        GLES20.glValidateProgram(programId)
        val status = IntArray(1)
        GLES20.glGetProgramiv(programId, GLES20.GL_VALIDATE_STATUS, status, 0)
        log("验证program结果为:${status[0]} \n ${GLES20.glGetProgramInfoLog(programId)}")
        return status[0] != 0
    }
}
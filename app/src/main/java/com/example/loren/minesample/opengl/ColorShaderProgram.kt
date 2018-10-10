package com.example.loren.minesample.opengl

import android.content.Context
import android.opengl.GLES20
import com.example.loren.minesample.R

/**
 * Copyright © 2018/9/12 by loren
 */
class ColorShaderProgram(context: Context) : ShaderProgram(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader) {

    //uniform
    var uMatrixLocation = GLES20.glGetUniformLocation(programId, U_MATRIX)
    //attribute
    var aPositionLocation = GLES20.glGetAttribLocation(programId, A_POSITION)
    var uColorLocation = GLES20.glGetUniformLocation(programId, U_COLOR)

    fun setUniforms(matrix: FloatArray, r: Float, g: Float, b: Float) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
        GLES20.glUniform4f(uColorLocation, r, g, b, 1f)
    }
}
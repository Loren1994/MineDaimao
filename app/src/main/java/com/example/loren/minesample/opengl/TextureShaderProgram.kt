package com.example.loren.minesample.opengl

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.GL_TEXTURE0
import android.opengl.GLES20.GL_TEXTURE_2D
import com.example.loren.minesample.R

/**
 * Copyright © 2018/9/12 by loren
 */
class TextureShaderProgram(context: Context) : ShaderProgram(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader) {
    //uniform
    var uMatrixLocation = GLES20.glGetUniformLocation(programId, U_MATRIX)
    var uTextureUnitLocation = GLES20.glGetUniformLocation(programId, U_TEXTURE_UNIT)
    //attribute
    var aPositionLocation = GLES20.glGetAttribLocation(programId, A_POSITION)
    var aTextureCoordinatesLocation = GLES20.glGetAttribLocation(programId, A_TEXTURE_COORDINATES)

    fun setUniforms(matrix: FloatArray, textureId: Int) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
        GLES20.glActiveTexture(GL_TEXTURE0)
        GLES20.glBindTexture(GL_TEXTURE_2D, textureId)
        GLES20.glUniform1i(uTextureUnitLocation, 0)
    }
}
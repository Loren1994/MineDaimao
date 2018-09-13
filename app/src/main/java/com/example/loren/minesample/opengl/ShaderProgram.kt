package com.example.loren.minesample.opengl

import android.content.Context
import android.opengl.GLES20

/**
 * Copyright © 2018/9/12 by loren
 */
open class ShaderProgram(context: Context, vertexSourceId: Int, fragmentSourceId: Int) {
    //uniform
    val U_MATRIX = "u_Matrix"
    val U_TEXTURE_UNIT = "u_TextureUnit"
    //attribute
    var A_COLOR = "a_Color"
    val A_POSITION = "a_Position"
    val A_TEXTURE_COORDINATES = "a_TextureCoordinates"
    //program
    var programId = ShaderHelper.buildProgram(
            TextSourceReader.readTextFileFromSource(context, vertexSourceId),
            TextSourceReader.readTextFileFromSource(context, fragmentSourceId))

    fun useProgram() {
        GLES20.glUseProgram(programId)
    }

}
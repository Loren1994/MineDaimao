package com.example.loren.minesample.opengl

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.example.loren.minesample.R
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Copyright © 2018/9/5 by loren
 */
class FirstGlRender(val context: Context) : GLSurfaceView.Renderer {

    private var projectionMatrix = FloatArray(16)
    private var modelMatrix = FloatArray(16)
    private lateinit var textureShaderProgram: TextureShaderProgram
    private lateinit var colorShaderProgram: ColorShaderProgram
    private var table = Table()
    private var mallet = Mallet()
    private var texture = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 0f)
        textureShaderProgram = TextureShaderProgram(context)
        colorShaderProgram = ColorShaderProgram(context)
        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        //table
        textureShaderProgram.useProgram()
        textureShaderProgram.setUniforms(projectionMatrix, texture)
        table.bindData(textureShaderProgram)
        table.draw()
        //mallet
        colorShaderProgram.useProgram()
        colorShaderProgram.setUniforms(projectionMatrix)
        mallet.bindData(colorShaderProgram)
        mallet.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        Matrix.perspectiveM(projectionMatrix, 0, 45f, width.toFloat() / height.toFloat(), 1f, 10f)
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -3f)
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f)
        val temp = FloatArray(16)
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0)
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.size)
    }
}
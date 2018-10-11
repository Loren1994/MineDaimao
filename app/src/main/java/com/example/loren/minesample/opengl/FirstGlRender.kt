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
    private var texture = 0
    private var viewMatrix = FloatArray(16)
    private var viewProjectionMatrix = FloatArray(16)
    private var modelViewProjectionMatrix = FloatArray(16)
    private lateinit var puck: Puck
    private lateinit var mallet: Mallet

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 0f)
        textureShaderProgram = TextureShaderProgram(context)
        colorShaderProgram = ColorShaderProgram(context)
        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface)
        puck = Puck(0.06f, 0.02f, 32)
        mallet = Mallet(0.08f, 0.15f, 32)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
        //draw table
        positionTableInScene()
        textureShaderProgram.useProgram()
        textureShaderProgram.setUniforms(modelViewProjectionMatrix, texture)
        table.bindData(textureShaderProgram)
        table.draw()
        //draw mallet
        positionObjectInScene(0f, mallet.height / 2f, -0.4f)
        colorShaderProgram.useProgram()
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f)
        mallet.bindData(colorShaderProgram)
        mallet.draw()
        positionObjectInScene(0f, mallet.height / 2f, 0.4f)
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f)
        mallet.draw()
        //draw puck
        positionObjectInScene(0f, puck.height / 2f, 0f)
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f)
        puck.bindData(colorShaderProgram)
        puck.draw()

        if (eyeX > 0.0f) eyeX -= 0.01f
        if (eyeY < 1.2f) eyeY += 0.01f
        if (eyeZ < 2.2f) eyeZ += 0.02f
        if (centerY < 0f) centerY += 0.01f
//        if (centerZ < 0f) centerZ += 0.02f
//        if (upX < 0f) upX += 1f
        Matrix.setLookAtM(viewMatrix, 0,
                eyeX, eyeY, eyeZ,
                0f, centerY, centerZ,
                upX, 1f, 0f)
    }

    var eyeX = 1f
    var eyeY = 1f
    var eyeZ = 1f
    var centerY = -1f
    var centerZ = 0f
    var upX = 0f
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        Matrix.perspectiveM(projectionMatrix, 0, 50f, width.toFloat() / height.toFloat(), 1f, 10f)
        Matrix.setLookAtM(viewMatrix, 0,
                eyeX, eyeY, eyeZ,
                0f, centerY, centerZ,
                0f, 1f, 0f)
    }

    private fun positionTableInScene() {
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f)
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0)
    }

    private fun positionObjectInScene(x: Float, y: Float, z: Float) {
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, x, y, z)
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0)

    }
}
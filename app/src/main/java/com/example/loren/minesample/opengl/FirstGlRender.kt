package com.example.loren.minesample.opengl

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.example.loren.minesample.R
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Copyright © 2018/9/5 by loren
 */
class FirstGlRender(val context: Context) : GLSurfaceView.Renderer {

    private var tableVerticlesWithTriangles = floatArrayOf(
            //x,y,r,g,b
            0f, 0f, 1f, 1f, 1f,
            -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
            0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
            0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
            -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
            -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
            //line & point
            -0.5f, 0f, 1f, 0f, 0f,
            0.5f, 0f, 1f, 0f, 0f,
            0f, -0.4f, 0f, 0f, 1f,
            0f, 0.4f, 1f, 0f, 0f
    )
    private var vertexData: FloatBuffer
    private var programId = 0
    private var A_COLOR = "a_Color"
    private var aColorLocation = 0
    private var A_POSITION = "a_Position"
    private var aPositionLocation = 0
    private var U_MATRIX = "u_Matrix"
    private var uMatrixLocation = 0
    private var projectionMatrix = FloatArray(16)
    private var modelMatrix = FloatArray(16)
    private var POSITION_COMPONENT_COUNT = 2
    private var COLOR_COMPONENT_COUNT = 3
    private var BYTE_PER_FLOAT = 4
    private var STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTE_PER_FLOAT

    init {
        vertexData = ByteBuffer
                .allocateDirect(tableVerticlesWithTriangles.size * BYTE_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        vertexData.put(tableVerticlesWithTriangles)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GL_COLOR_BUFFER_BIT)
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0)
        GLES20.glDrawArrays(GL_TRIANGLE_FAN, 0, 6)
        GLES20.glDrawArrays(GL_LINES, 6, 2)
        GLES20.glDrawArrays(GL_POINTS, 8, 1)
        GLES20.glDrawArrays(GL_POINTS, 9, 1)
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

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        val vertexSource = TextSourceReader.readTextFileFromSource(context, R.raw.simple_vertex_shader)
        val fragmentSource = TextSourceReader.readTextFileFromSource(context, R.raw.simple_fragment_shader)
        val vertexShader = ShaderHelper.compileVertexSahder(vertexSource)
        val fragmentShader = ShaderHelper.compileFragmentShader(fragmentSource)
        programId = ShaderHelper.linkProgram(vertexShader, fragmentShader)
        ShaderHelper.validateProgram(programId)
        GLES20.glUseProgram(programId)

        uMatrixLocation = GLES20.glGetUniformLocation(programId, U_MATRIX)
        aColorLocation = GLES20.glGetAttribLocation(programId, A_COLOR)
        aPositionLocation = GLES20.glGetAttribLocation(programId, A_POSITION)
        vertexData.position(0)
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData)
        GLES20.glEnableVertexAttribArray(aPositionLocation)
        vertexData.position(POSITION_COMPONENT_COUNT)
        GLES20.glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData)
        GLES20.glEnableVertexAttribArray(aColorLocation)
    }
}
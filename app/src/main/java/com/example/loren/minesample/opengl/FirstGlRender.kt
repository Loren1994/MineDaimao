package com.example.loren.minesample.opengl

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
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

    //只能画点/线/三角形
    private var tableVerticlesWithTrianglesOrigin = floatArrayOf(
            //Triangles 1
            0f, 0f,
            9f, 14f,
            0f, 14f,
            //Triangles 2
            0f, 0f,
            9f, 0f,
            9f, 14f,
            //line
            0f, 7f,
            9f, 7f,
            //point
            4.5f, 2f,
            4.5f, 12f
    )
    //屏幕顶点为(-1,1)(1,1)(-1,-1)(1,-1)
    private var tableVerticlesWithTriangles = floatArrayOf(
            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0f,
            0.5f, 0f,
            0f, -0.25f,
            0f, 0.25f
    )
    private var POSITION_COMPONENT_COUNT = 2
    private var BYTE_PER_FLOAT = 4
    private var vertexData: FloatBuffer
    private var programId = 0
    private var U_COLOR = "u_Color"
    private var uColorLocation = 0
    private var A_POSITION = "a_Position"
    private var aPositionLocation = 0

    init {
        vertexData = ByteBuffer
                .allocateDirect(tableVerticlesWithTriangles.size * BYTE_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        vertexData.put(tableVerticlesWithTriangles)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GL_COLOR_BUFFER_BIT)
        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        GLES20.glDrawArrays(GL_TRIANGLES, 0, 6)
        GLES20.glUniform4f(uColorLocation, 1.0f, 0f, 0f, 1f)
        GLES20.glDrawArrays(GL_LINES, 6, 2)
        GLES20.glUniform4f(uColorLocation, 0.0f, 0f, 1f, 1f)
        GLES20.glDrawArrays(GL_POINTS, 8, 1)
        GLES20.glUniform4f(uColorLocation, 0.0f, 0f, 1f, 1f)
        GLES20.glDrawArrays(GL_POINTS, 9, 1)

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
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

        uColorLocation = GLES20.glGetUniformLocation(programId, U_COLOR)
        aPositionLocation = GLES20.glGetAttribLocation(programId, A_POSITION)
        vertexData.position(0)
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData)
        GLES20.glEnableVertexAttribArray(aPositionLocation)
    }
}
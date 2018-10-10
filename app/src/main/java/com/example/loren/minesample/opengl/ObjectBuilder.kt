package com.example.loren.minesample.opengl

import android.opengl.GLES20
import com.example.loren.minesample.base.ext.log

/**
 * Copyright © 2018/9/18 by loren
 */
class ObjectBuilder(vertexSize: Int) {
    private val FLOAT_PER_VERTEX = 3
    private var offset = 0
    private val vertexData = FloatArray(vertexSize * FLOAT_PER_VERTEX)
    private var drawList = arrayListOf<DrawCommand>()

    companion object {
        fun sizeOfCircleVertices(numPoint: Int): Int {
            return (numPoint + 1) + 1
        }

        fun sizeOfOpenCylinderVertices(numPoint: Int): Int {
            return (numPoint + 1) * 2
        }

        fun createPuck(cylinder: Geometry.Cylinder, numPoint: Int): GeneratedData {
            val size = sizeOfCircleVertices(numPoint) + sizeOfOpenCylinderVertices(numPoint)
            val objectBuilder = ObjectBuilder(size)
            val puckTop = Geometry.Circle(cylinder.radius, cylinder.center.translateY(cylinder.height / 2f))
            objectBuilder.appendCircle(puckTop, numPoint)
            objectBuilder.appendOpenCylinder(cylinder, numPoint)
            return objectBuilder.build()
        }

        fun createMallet(center: Geometry.Point, radius: Float, height: Float, numPoint: Int): GeneratedData {
            val size = sizeOfCircleVertices(numPoint) * 2 + sizeOfOpenCylinderVertices(numPoint) * 2
            val objectBuilder = ObjectBuilder(size)
            val baseHeight = 0.25 * height
            val baseCircle = Geometry.Circle(radius, center.translateY((-baseHeight).toFloat()))
            val baseCylinder = Geometry.Cylinder(radius, baseHeight.toFloat(), baseCircle.center.translateY((-baseHeight / 2f).toFloat()))
            objectBuilder.appendCircle(baseCircle, numPoint)
            objectBuilder.appendOpenCylinder(baseCylinder, numPoint)
            val handleRadius = radius / 3f
            val handleHeight = height * 0.75f
            val handleCircle = Geometry.Circle(handleRadius, center.translateY(height * 0.5f))
            val handleCylinder = Geometry.Cylinder(handleRadius, handleHeight, handleCircle.center.translateY(-handleHeight / 2f))
            objectBuilder.appendCircle(handleCircle, numPoint)
            objectBuilder.appendOpenCylinder(handleCylinder, numPoint)
            return objectBuilder.build()
        }
    }

    fun appendCircle(circle: Geometry.Circle, numPoint: Int) {
        val startVertex = offset / FLOAT_PER_VERTEX
        val numVertex = sizeOfCircleVertices(numPoint)
        vertexData[offset++] = circle.center.x
        vertexData[offset++] = circle.center.y
        vertexData[offset++] = circle.center.z
        repeat(numPoint + 1) {
            val angleRadians = (it.toFloat() / numPoint.toFloat()) * Math.PI * 2f
            vertexData[offset++] = circle.center.x + circle.radius * Math.cos(angleRadians).toFloat()
            vertexData[offset++] = circle.center.y
            vertexData[offset++] = circle.center.z + circle.radius * Math.sin(angleRadians).toFloat()
        }
        drawList.add(object : DrawCommand {
            override fun draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, startVertex, numVertex)
            }
        })
    }

    fun appendOpenCylinder(cylinder: Geometry.Cylinder, numPoint: Int) {
        val startVertex = offset / FLOAT_PER_VERTEX
        val numVertex = sizeOfOpenCylinderVertices(numPoint)
        val yStart = cylinder.center.y - (cylinder.height / 2f)
        val yEnd = cylinder.center.y + (cylinder.height / 2f)
        repeat(numPoint + 1) {
            val angleRadians = (it.toFloat() / numPoint.toFloat()) * Math.PI * 2f
            val xPos = cylinder.center.x + cylinder.radius * Math.cos(angleRadians)
            val zPos = cylinder.center.z + cylinder.radius * Math.sin(angleRadians)
            vertexData[offset++] = xPos.toFloat()
            vertexData[offset++] = yStart
            vertexData[offset++] = zPos.toFloat()
            vertexData[offset++] = xPos.toFloat()
            vertexData[offset++] = yEnd
            vertexData[offset++] = zPos.toFloat()
        }
        drawList.add(object : DrawCommand {
            override fun draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, startVertex, numVertex)
            }
        })
    }

    interface DrawCommand {
        fun draw()
    }

    fun build(): GeneratedData {
        return GeneratedData(vertexData, drawList)
    }

    class GeneratedData(val vertexData: FloatArray, val drawList: ArrayList<DrawCommand>)
}
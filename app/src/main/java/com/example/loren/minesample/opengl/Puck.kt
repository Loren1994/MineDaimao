package com.example.loren.minesample.opengl

/**
 * Copyright © 2018/9/30 by loren
 */
class Puck(val radius: Float, val height: Float, numPointAroundPuck: Int) {
    private val POSITION_COMPONENT_COUNT = 3
    private var vertexArray: VertexArray
    private var drawList: ArrayList<ObjectBuilder.DrawCommand>

    init {
        val generatedData = ObjectBuilder.createPuck(
                Geometry.Cylinder(radius, height,
                        Geometry.Point(0f, 0f, 0f)), numPointAroundPuck)
        vertexArray = VertexArray(generatedData.vertexData)
        drawList = generatedData.drawList
    }

    fun bindData(colorShaderProgram: ColorShaderProgram) {
        vertexArray.setVertexAttributePoint(0,
                colorShaderProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT, 0)
    }

    fun draw() {
        drawList.forEach { it.draw() }
    }
}
package com.example.loren.minesample.opengl

/**
 * Copyright © 2018/9/12 by loren
 */
class Mallet(val radius: Float, val height: Float, numPointAroundMallet: Int) {
    private val POSITION_COMPONENT_COUNT = 3
    private var vertexArray: VertexArray
    private var drawList: ArrayList<ObjectBuilder.DrawCommand>

    init {
        val generatedData = ObjectBuilder.createMallet(
                Geometry.Point(0f, 0f, 0f), radius, height, numPointAroundMallet)
        vertexArray = VertexArray(generatedData.vertexData)
        drawList = generatedData.drawList
    }

    fun bindData(shaderProgram: ColorShaderProgram) {
        vertexArray.setVertexAttributePoint(0,
                shaderProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT, 0)
    }

    fun draw() {
        drawList.forEach { it.draw() }
    }

}
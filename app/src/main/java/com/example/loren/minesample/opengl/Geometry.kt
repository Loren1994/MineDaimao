package com.example.loren.minesample.opengl

/**
 * Copyright © 2018/9/18 by loren
 */
object Geometry {

    class Point(val x: Float, val y: Float, val z: Float) {

        fun translateY(distance: Float): Point {
            return Point(x, y + distance, z)
        }
    }

    class Circle(val radius: Float, val center: Point) {
        fun scale(scale: Float): Circle {
            return Circle(radius * scale, center)
        }
    }

    class Cylinder(val radius: Float, val height: Float, val center: Point)

    class Vector(x: Float, y: Float, z: Float)

    class Ray(point: Point, vector: Vector)

    fun vectorBetween(from: Point, to: Point): Vector {
        return Vector(to.x - from.x,
                to.y - from.y,
                to.z - from.z)
    }
}
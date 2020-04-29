package ru.laink.ball.models

import android.graphics.Canvas
import android.graphics.Rect
import kotlin.math.pow
import kotlin.math.sqrt

open class Circle(
    color: Int,
    private val x: Int,
    private val y: Int,
    private val radius: Int
) : GameElement(color, x, y, 2 * radius, 2 * radius) {

    private fun distanceToBall(ball: CannonBall): Double {
        return sqrt(
            (ball.shape.centerX() - shape.centerX()).toDouble().pow(2.0) +
                    (ball.shape.centerY() - shape.centerY()).toDouble().pow(2.0)
        )
    }

    fun checkIntercept(ball: CannonBall, const: Double): Boolean {
        return distanceToBall(ball) < radius * const/*1.1*/
//        return Rect.intersects(ball.shape, shape)
    }

    // Прорисовка  Canvas
    override fun draw(canvas: Canvas) {
        canvas.drawCircle(
            (x + radius + 20).toFloat(),
            (y + radius + 20).toFloat(),
            radius.toFloat(),
            paint
        )
    }
}
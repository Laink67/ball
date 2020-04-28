package ru.laink.ball.models

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

open class GameElement(
    color: Int,
    x: Int,
    y: Int,
    width: Int,
    height: Int
) {
    val paint = Paint()
    var shape: Rect

    init {
        paint.color = color
        shape = Rect(x, y, x + width, y + height)
    }

    // Прорисовка GameElement на Canvas
    open fun draw(canvas: Canvas) {
        canvas.drawRect(shape, paint)
    }

}
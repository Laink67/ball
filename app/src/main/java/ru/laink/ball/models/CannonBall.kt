package ru.laink.ball.models

import android.content.res.Resources
import android.graphics.*
import ru.laink.ball.R
import ru.laink.ball.other.BallDirection
import ru.laink.ball.views.LabyrinthView
import java.util.*
import kotlin.math.abs

class CannonBall(
    private val view: LabyrinthView,
    color: Int,
    x: Int,
    y: Int,
    private val radius: Int,
    var velocityX: Float,
    private var velocityY: Float
) : GameElement(color, x, y, 2 * radius, 2 * radius) {

    init {
        this.velocityY = -velocityY
        shape = Rect(x, y, x + 2 * radius, y + 2 * radius)
    }

    // Проверка на столкновение с каким-либо GameElement
    private fun collidesWith(platform: Platform): Boolean {
        return (Rect.intersects(shape, platform.shape))
    }

    private fun intersectPlatform(direction: BallDirection, platforms: LinkedList<Platform>) {
        for (platform in platforms) {
            if (collidesWith(platform)) {
                when (direction) {
                    BallDirection.N -> {
                        intersectsPlatformBottomBorder(platform)
                    }
                    BallDirection.NE -> {
                        intersectsPlatformBottomBorder(platform)
                        intersectsPlatformLeftBorder(platform)
                    }
                    BallDirection.E -> {
                        intersectsPlatformLeftBorder(platform)
                    }
                    BallDirection.SE -> {
                        intersectsPlatformLeftBorder(platform)
                        intersectsPlatformTopBorder(platform)
                    }
                    BallDirection.S -> {
                        intersectsPlatformTopBorder(platform)
                    }
                    BallDirection.SW -> {
                        intersectsPlatformTopBorder(platform)
                        intersectsPlatformRightBorder(platform)
                    }
                    BallDirection.W -> {
                        intersectsPlatformRightBorder(platform)
                    }
                    BallDirection.NW -> {
                        intersectsPlatformRightBorder(platform)
                        intersectsPlatformBottomBorder(platform)
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun intersectsPlatformLeftBorder(platform: Platform) {
        // Столкновение с левой границей
        if (abs(shape.right - platform.shape.left) < radius / 2) {
            shape.right = platform.shape.left - 1
            shape.left = shape.right - radius * 2
        }
    }

    private fun intersectsPlatformRightBorder(platform: Platform) {
        // Столкновение с правой границей
        if (abs(shape.left - platform.shape.right) < radius / 2) {
            shape.left = platform.shape.right + 1
            shape.right = shape.left + radius * 2
        }
    }

    private fun intersectsPlatformTopBorder(platform: Platform) {
        // Столкновение с верхней границей
        if (abs(shape.bottom - platform.shape.top) < radius / 2) {
            shape.bottom = platform.shape.top - 1
            shape.top = shape.bottom - radius * 2
        }
    }

    private fun intersectsPlatformBottomBorder(platform: Platform) {
        // Столкновение с нижней границей
        if (abs(shape.top - platform.shape.bottom) < radius / 2) {
            shape.top = platform.shape.bottom + 1
            shape.bottom = shape.top + radius * 2
        }
    }


    private fun getDirection(speedX: Float, speedY: Float): BallDirection {
        return when {
            speedX > 0 -> {
                when {
                    speedY > 0 -> {
                        BallDirection.SE
                    }
                    speedY < 0 -> {
                        BallDirection.NE
                    }
                    else -> {
                        BallDirection.E
                    }
                }
            }
            speedX < 0 -> {
                when {
                    speedY > 0 -> {
                        BallDirection.SW
                    }
                    speedY < 0 -> {
                        BallDirection.NW
                    }
                    else -> {
                        BallDirection.W
                    }
                }
            }
            else -> {
                when {
                    speedY > 0 -> {
                        BallDirection.S
                    }
                    speedY < 0 -> {
                        BallDirection.N
                    }
                    else -> {
                        BallDirection.STILL
                    }
                }
            }
        }
    }


    private fun intersectsWithRightBorder() {
        // Столкновение с правой границей
        if (shape.right >= view.screenWidth - 1) {
            shape.right = view.screenWidth - 2
            shape.left = shape.right - radius * 2
        }
    }

    private fun intersectsWithLeftBorder() {
        // Столкновение с левой границей
        if (shape.left <= 0 + 1) {
            shape.left = 0 + 2
            shape.right = shape.left + radius * 2
        }
    }

    private fun intersectsWithBottomBorder() {
        // Столкновение с нижней границей
        if (shape.bottom >= view.screenHeight - 1) {
            shape.bottom = view.screenHeight - 2
            shape.top = shape.bottom - radius * 2
        }
    }

    private fun intersectsWithTopBorder() {
        // Столкновение с верхней границей
        if (shape.top <= 0 + 0.5) {
            shape.top = 1
            shape.bottom = shape.top + radius * 2
        }
    }

    // Изменение вертикальной позиции ядра
    fun update(xAccel: Float, yAccel: Float, platforms: LinkedList<Platform>) {
        val speedX = velocityX * xAccel
        val speedY = velocityY * yAccel

        val direction = getDirection(speedX, speedY)

        // Проверка столкновений
        when (direction) {
            BallDirection.N -> {
                intersectsWithTopBorder()
            }
            BallDirection.NE -> {
                intersectsWithTopBorder()
                intersectsWithRightBorder()
            }
            BallDirection.E -> {
                intersectsWithRightBorder()
            }
            BallDirection.SE -> {
                intersectsWithBottomBorder()
                intersectsWithRightBorder()
            }
            BallDirection.S -> {
                intersectsWithBottomBorder()
            }
            BallDirection.SW -> {
                intersectsWithLeftBorder()
                intersectsWithBottomBorder()
            }
            BallDirection.W -> {
                intersectsWithLeftBorder()
            }
            BallDirection.NW -> {
                intersectsWithLeftBorder()
                intersectsWithTopBorder()
            }
            else -> {
            }
        }

        intersectPlatform(direction, platforms)

        val tmpX = (velocityX * xAccel * 0.666f / 12).toInt()
        val tmpY = (velocityY * yAccel * 0.666f / 12).toInt()
        // Перемещение
        shape.offset(
            (velocityX * xAccel * 0.666f / 12).toInt(),
            (velocityY * yAccel * 0.666f / 12).toInt()
        )
    }

    fun draw(canvas: Canvas, resources: Resources) {
        val ballSrc = BitmapFactory.decodeResource(resources, R.drawable.ball)
        val ball = Bitmap.createScaledBitmap(
            ballSrc, 100,
            100, true
        )

//        paint.pathEffect = DiscretePathEffect(60f, 2f)
//        canvas.drawCircle(
//            shape.left + getRadius()/* + radius * 10*//*(view.screenWidth / 2).toFloat()*/,
//            shape.top + getRadius() /*+ getRadius()*//*(view.screenHeight / 2).toFloat()*/,
//            /*getRadius()*/getRadius(),
//            paint
//        )

        canvas.drawBitmap(
            ball,
            shape.left.toFloat(),
            shape.top.toFloat(),
            paint
        )
    }
}
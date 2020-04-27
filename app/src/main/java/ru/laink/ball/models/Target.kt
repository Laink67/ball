package ru.laink.ball.models

import ru.laink.ball.views.LabyrinthView

// Мишень
class Target(
    private val view: LabyrinthView,
    color: Int,
    x: Int,
    y: Int,
    private val radius: Int
) : Circle(color, x, y, radius) {

    fun checkIntercept(ball: CannonBall) {
        if (distanceToBall(ball) < radius / 2) {
        }
    }
}
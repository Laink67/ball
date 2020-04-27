package ru.laink.ball.models

class Let(
    color: Int,
    x: Int,
    y: Int,
    private val radius: Int
) : Circle(color, x, y, radius) {

//    fun checkIntercept(ball: CannonBall): Boolean {
//        return distanceToBall(ball) < radius*1.1
/*
        return Rect.intersects(
            ball.shape,shape
            */
/*Rect(shape.left + 5, shape.top + 5, shape.right - 5, shape.bottom - 5)*//*

        )
*/
//    }
}
package ru.laink.ball.models

// Мишень
class Target(
    color: Int,
    x: Int,
    y: Int,
    radius: Int
) : Circle(color, x, y, radius) {}
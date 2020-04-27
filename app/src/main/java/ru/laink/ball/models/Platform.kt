package ru.laink.ball.models

import android.graphics.*
import android.graphics.drawable.Drawable

class Platform(
    color: Int,
    x: Int,
    y: Int,
    width: Int,
    height: Int
) : GameElement(color, x, y, width, height) {}
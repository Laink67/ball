package ru.laink.ball.controllers

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import ru.laink.ball.R
import ru.laink.ball.models.CannonBall
import ru.laink.ball.models.Let
import ru.laink.ball.models.Platform
import ru.laink.ball.other.Constant.Companion.COLOR_PLATFORM
import ru.laink.ball.other.Constant.Companion.HORIZONTAL_PLATFORM_HEIGHT
import ru.laink.ball.other.Constant.Companion.MAX_ACCEL
import ru.laink.ball.other.Constant.Companion.STARTING_VELOCITY
import ru.laink.ball.other.Constant.Companion.VERTICAL_PLATFORM_WIDTH
import ru.laink.ball.views.LabyrinthView
import java.util.*
import kotlin.math.abs
import kotlin.math.sign

class MainController(
    private val context: Context
) {
    private val COLOR_TARGET = context.resources.getColor(R.color.target)

    private var xAccel = 0.0f
    private var yAccel = 0.0f
    private var velocityX = STARTING_VELOCITY
    private var velocityY = STARTING_VELOCITY

    // Переменные Paint для рисования элементов на экране
    private var backGroundPaint: Paint // Для стирания области рисования

    val view = LabyrinthView(context, this)

    private var sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    lateinit var ball: CannonBall
    lateinit var target: ru.laink.ball.models.Target
    private lateinit var drawablePlatforms: ArrayList<Platform>
    private lateinit var lets: ArrayList<Let>

    private var rand: Random

    private var lvl = 1

    init {
        // Регистрация слушателя SurfaceHolder.CallBack для получения методов изменения состояния SurfaceView
        view.holder.addCallback(view)

        backGroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backGroundPaint.color = Color.WHITE

        rand = Random()
    }


    private var sensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                xAccel = event.values[1]
                yAccel = -event.values[0]

                // Проверка, чтобы сильно не менялась скорость
                if (abs(xAccel) > MAX_ACCEL)
                    xAccel = MAX_ACCEL * sign(xAccel)

                // Проверка, чтобы сильно не менялась скорость
                if (abs(yAccel) > MAX_ACCEL)
                    yAccel = MAX_ACCEL * sign(yAccel)

                update()
            }
        }
    }


    fun newGame() {

        when (lvl) {
            1 -> createElementsFirstLvl()
            2 -> createElementsSecondLvl()
            3 -> createElementsThirdLvl()
            4 -> createElementsFourthLvl()
            5 -> createElementsFifthLvl()
            6 -> createElementsSixthLvl()
            7 -> createElementsSeventhLvl()
            else -> {
                createElementsFirstLvl()
                lvl = 1
            }
        }
    }

    private fun createElementsFirstLvl() {
        val radius = ((3.0 / 52) * view.getSurfaceHeight()).toInt()
        val radiusBall = ((3.0 / 55) * view.getSurfaceHeight()).toInt()

        ball = CannonBall(
            view,
            Color.RED,
            view.screenWidth - radius,
            view.screenHeight - radius,
            radiusBall,
            velocityX,
            velocityY
        )

        target = ru.laink.ball.models.Target(
            COLOR_TARGET,
            (view.screenWidth * 0.4).toInt(),
            (view.screenHeight * 0.33).toInt(),
            radius
        )

        lets = arrayListOf(
            Let(
                Color.BLACK,
                (view.screenWidth * 0.25).toInt(),
                (view.screenHeight * 0.35).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.25).toInt(),
                (view.screenHeight * 0.1).toInt(),
                radius
            )
        )

        drawablePlatforms = arrayListOf(
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.15).toInt(),
                (view.screenHeight * 0.75).toInt(),
                view.screenWidth,
                HORIZONTAL_PLATFORM_HEIGHT
            ),
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.5).toInt(),
                0,
                VERTICAL_PLATFORM_WIDTH,
                view.screenHeight / 2
            ),
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.3).toInt(),
                (view.screenHeight * 0.5).toInt(),
                (view.screenWidth * 0.83).toInt(),
                HORIZONTAL_PLATFORM_HEIGHT
            )
        )
    }

    private fun createElementsSeventhLvl() {
        val radius = ((3.0 / 52) * view.getSurfaceHeight()).toInt()
        val radiusBall = ((3.0 / 55) * view.getSurfaceHeight()).toInt()

        ball = CannonBall(
            view,
            Color.RED,
            view.screenWidth - radius,
            view.screenHeight - radius,
            radiusBall,
            velocityX,
            velocityY
        )

        target = ru.laink.ball.models.Target(
            COLOR_TARGET,
            (view.screenWidth * 0.4).toInt(),
            (view.screenHeight * 0.33).toInt(),
            radius
        )

        lets = arrayListOf(
            Let(
                Color.BLACK,
                (view.screenWidth * 0.65).toInt(),
                (view.screenHeight * 0.7).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.7).toInt(),
                (view.screenHeight * 0.6).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.75).toInt(),
                (view.screenHeight * 0.5).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.77).toInt(),
                (view.screenHeight * 0.38).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.75).toInt(),
                (view.screenHeight * 0.25).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.65).toInt(),
                (view.screenHeight * 0.2).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.65).toInt(),
                (view.screenHeight * 0.34).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.62).toInt(),
                (view.screenHeight * 0.45).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.55).toInt(),
                (view.screenHeight * 0.45).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.48).toInt(),
                (view.screenHeight * 0.45).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.41).toInt(),
                (view.screenHeight * 0.45).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.35).toInt(),
                (view.screenHeight * 0.4).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.33).toInt(),
                (view.screenHeight * 0.28).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.38).toInt(),
                (view.screenHeight * 0.2).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.38).toInt(),
                -radius / 2,
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.5).toInt(),
                (view.screenHeight * 0.2).toInt(),
                radius
            )
        )

        drawablePlatforms = arrayListOf(
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.15).toInt(),
                (view.screenHeight * 0.75).toInt(),
                (view.screenWidth * 0.5).toInt(),
                HORIZONTAL_PLATFORM_HEIGHT
            ),
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.67).toInt(),
                0,
                VERTICAL_PLATFORM_WIDTH,
                (view.screenHeight * 0.2).toInt()
            ),
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.15).toInt(),
                0,
                VERTICAL_PLATFORM_WIDTH,
                view.screenHeight
            )
        )
    }

    private fun createElementsSixthLvl() {
        val radius = ((3.0 / 52) * view.getSurfaceHeight()).toInt()
        val radiusBall = ((3.0 / 55) * view.getSurfaceHeight()).toInt()

        // Создание нового ball
        ball = CannonBall(
            view,
            Color.RED,
            view.screenWidth - radius,
            view.screenHeight - radius,
            radiusBall,
            velocityX,
            velocityY
        )

        target = ru.laink.ball.models.Target(
            COLOR_TARGET,
            radius, radius,
            radius
        )

        lets = arrayListOf(
            Let(
                Color.BLACK,
                (view.screenWidth * 0.8).toInt(),
                view.screenHeight - 2 * radius,
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.4).toInt(),
                (view.screenHeight * 0.77).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                radius,
                (view.screenHeight * 0.81).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.08).toInt(),
                (view.screenHeight * 0.4).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.165).toInt(),
                (view.screenHeight * 0.4).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.4).toInt(),
                (view.screenHeight * 0.23).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.51).toInt(),
                (view.screenHeight * 0.35).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.8).toInt(),
                (view.screenHeight * 0.49).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.84).toInt(),
                (view.screenHeight * 0.3).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth - 1.8 * radius).toInt(),
                (view.screenHeight * 0.3).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.8).toInt(),
                (radius * 1.3).toInt(),
                radius
            ),
            Let(
                Color.BLACK,
                (view.screenWidth * 0.3).toInt(),
                (radius * 1.3).toInt(),
                radius
            )
        )

        drawablePlatforms = arrayListOf(
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.15).toInt(),
                (view.screenHeight * 0.75).toInt(),
                view.screenWidth,
                HORIZONTAL_PLATFORM_HEIGHT
            ),
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.15).toInt(),
                (view.screenHeight * 0.46).toInt(),
                VERTICAL_PLATFORM_WIDTH,
                (view.screenHeight * 0.3).toInt()
            ),
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.5).toInt(),
                (view.screenHeight * 0.46).toInt(),
                VERTICAL_PLATFORM_WIDTH,
                (view.screenHeight * 0.3).toInt()
            ),
            Platform(
                COLOR_PLATFORM,
                0,
                (view.screenHeight * 0.2).toInt(),
                (view.screenWidth * 0.83).toInt(),
                HORIZONTAL_PLATFORM_HEIGHT
            ),
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.83).toInt(),
                (view.screenHeight * 0.2).toInt(),
                VERTICAL_PLATFORM_WIDTH,
                (view.screenHeight * 0.3).toInt()
            )
        )
    }

    private fun createElementsFifthLvl() {
        val radius = ((3.0 / 52) * view.getSurfaceHeight()).toInt()
        val radiusBall = ((3.0 / 55) * view.getSurfaceHeight()).toInt()

        // Создание нового ball
        ball = CannonBall(
            view,
            Color.RED,
            radius,
            view.screenHeight,
            radiusBall,
            velocityX,
            velocityY
        )

        target = ru.laink.ball.models.Target(
            COLOR_TARGET,
            (view.screenWidth * 0.45).toInt(),
            (view.screenHeight * 0.5).toInt(),
            radius
        )

        lets =
            arrayListOf(
                Let(
                    Color.BLACK,
                    radius,
                    radius,
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.75).toInt(),
                    (view.screenHeight * 0.1).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth - 2.5 * radius).toInt(),
                    (view.screenHeight * 0.7).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.75).toInt(),
                    (view.screenHeight * 0.77).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.25).toInt(),
                    (view.screenHeight - 2.5 * radius).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.37).toInt(),
                    (view.screenHeight * 0.5).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.25).toInt(),
                    (view.screenHeight * 0.3).toInt(),
                    radius
                )
            )

        drawablePlatforms = arrayListOf(
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.2).toInt(),
                (view.screenHeight * 0.25).toInt(),
                VERTICAL_PLATFORM_WIDTH,
                view.screenHeight
            ),
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.2).toInt(),
                (view.screenHeight * 0.25).toInt(),
                (view.screenWidth * 0.6).toInt(),
                HORIZONTAL_PLATFORM_HEIGHT
            ),
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.8).toInt(),
                (view.screenHeight * 0.25).toInt(),
                VERTICAL_PLATFORM_WIDTH,
                view.screenHeight / 2
            ),
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.45).toInt(),
                (view.screenHeight * 0.74).toInt(),
                (view.screenWidth * 0.37).toInt() - 1,
                HORIZONTAL_PLATFORM_HEIGHT
            ),
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.44).toInt(),
                (view.screenHeight * 0.49).toInt(),
                VERTICAL_PLATFORM_WIDTH,
                (view.screenHeight * 0.29).toInt()
            ),
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.44).toInt(),
                (view.screenHeight * 0.47).toInt(),
                (view.screenWidth * 0.2).toInt(),
                HORIZONTAL_PLATFORM_HEIGHT
            )
        )
    }

    private fun createElementsFourthLvl() {
        val radius = ((3.0 / 52) * view.getSurfaceHeight()).toInt()
        val radiusBall = ((3.0 / 55) * view.getSurfaceHeight()).toInt()

        // Создание нового ball
        ball = CannonBall(
            view,
            Color.RED,
            view.screenWidth - 100,
            0,
            radiusBall,
            velocityX,
            velocityY
        )

        target = ru.laink.ball.models.Target(
            COLOR_TARGET,
            (view.screenWidth * 0.45).toInt(),
            (view.screenHeight * 0.45).toInt(),
            radius
        )

        drawablePlatforms = arrayListOf(
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.85).toInt(),
                (view.screenHeight * 0.2).toInt(),
                (view.screenWidth * 0.5).toInt(),
                HORIZONTAL_PLATFORM_HEIGHT
            )
        )

        lets =
            arrayListOf(
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.4).toInt() - radius,
                    (view.screenHeight * 0.45).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.4).toInt() - radius,
                    (view.screenHeight * 0.45 + 4.2 * radius).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.4).toInt() - radius,
                    (view.screenHeight * 0.45 + 2.1 * radius).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.4).toInt() - radius,
                    (view.screenHeight * 0.45 - 2.2 * radius).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.4 + 1.2 * radius).toInt(),
                    (view.screenHeight * 0.45 - 2.2 * radius).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.49).toInt(),
                    (view.screenHeight * 0.45 - 2.2 * radius).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.52).toInt(),
                    (view.screenHeight * 0.45).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.52).toInt(),
                    (view.screenHeight * 0.45 + 2.1 * radius).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.52).toInt(),
                    (view.screenHeight * 0.45 + 4.2 * radius).toInt(),
                    radius
                )
            )
    }

    private fun createElementsThirdLvl() {
        val radius = ((3.0 / 52) * view.getSurfaceHeight()).toInt()
        val radiusBall = ((3.0 / 55) * view.getSurfaceHeight()).toInt()

        // Создание нового ball
        ball = CannonBall(
            view,
            Color.RED,
            view.screenWidth - 100,
            0,
            radiusBall,
            velocityX,
            velocityY
        )

        target = ru.laink.ball.models.Target(
            COLOR_TARGET,
            0,
            view.screenHeight - 3 * radius,
            radius
        )

        // Создание платформ
        drawablePlatforms = arrayListOf(
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.1).toInt(),
                (view.screenHeight * 0.25).toInt(),
                view.screenWidth,
                HORIZONTAL_PLATFORM_HEIGHT
            ),
            Platform(
                COLOR_PLATFORM,
                0,
                (view.screenHeight * 0.5).toInt(),
                (view.screenWidth * 0.9).toInt(),
                HORIZONTAL_PLATFORM_HEIGHT
            ),
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.9).toInt(),
                (view.screenHeight * 0.5).toInt(),
                VERTICAL_PLATFORM_WIDTH,
                (view.screenHeight * 0.35).toInt()
            ),
            Platform(
                COLOR_PLATFORM,
                view.screenWidth / 2,
                (view.screenHeight * 0.7).toInt(),
                VERTICAL_PLATFORM_WIDTH,
                (view.screenHeight * 0.4).toInt()
            )
        )

        lets =
            arrayListOf(
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.5).toInt(),
                    0,
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.1).toInt(),
                    (view.screenHeight * 0.11).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.5).toInt(),
                    (view.screenHeight * 0.36).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    (view.screenWidth * 0.5).toInt() + VERTICAL_PLATFORM_WIDTH,
                    (view.screenHeight * 0.67).toInt(),
                    radius
                )
            )
    }


    private fun createElementsSecondLvl() {
        val radius = ((3.0 / 52) * view.getSurfaceHeight()).toInt()
        val radiusBall = ((3.0 / 55) * view.getSurfaceHeight()).toInt()

        // Создание нового ball
        ball = CannonBall(
            view,
            Color.RED,
            view.screenWidth - 100,
            0,
            radiusBall,
            velocityX,
            velocityY
        )

        target = ru.laink.ball.models.Target(
            COLOR_TARGET,
            0,
            0,
            radius
        )

        // Создание платформ
        drawablePlatforms = arrayListOf(
            Platform(
                COLOR_PLATFORM,
                view.screenWidth / 3,
                0,
                VERTICAL_PLATFORM_WIDTH,
                (view.screenHeight * 0.7).toInt()
            ), Platform(
                COLOR_PLATFORM,
                view.screenWidth / 2,
                view.screenHeight / 2,
                VERTICAL_PLATFORM_WIDTH,
                view.screenHeight
            ),
            Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.7).toInt(),
                0,
                VERTICAL_PLATFORM_WIDTH,
                (view.screenHeight * 0.7).toInt()
            ), Platform(
                COLOR_PLATFORM,
                (view.screenWidth * 0.85).toInt(),
                (view.screenHeight * 0.2).toInt(),
                (view.screenWidth * 0.5).toInt(),
                HORIZONTAL_PLATFORM_HEIGHT
            ),
            Platform(
                COLOR_PLATFORM,
                0,
                (view.screenHeight * 0.2).toInt(),
                (view.screenWidth * 0.2).toInt(),
                HORIZONTAL_PLATFORM_HEIGHT
            )
        )

        lets =
            arrayListOf(
                Let(
                    Color.BLACK, (view.screenWidth * 0.72).toInt(),
                    (view.screenHeight * 0.3).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    view.screenWidth / 2 - VERTICAL_PLATFORM_WIDTH,
                    (view.screenHeight * 0.37).toInt(),
                    radius
                ),
                Let(
                    Color.BLACK,
                    view.screenWidth / 2 - VERTICAL_PLATFORM_WIDTH,
                    0,
                    radius
                ),
                Let(
                    Color.BLACK,
                    view.screenWidth / 3 - 2 * VERTICAL_PLATFORM_WIDTH,
                    (view.screenHeight * 0.7).toInt(),
                    radius
                )
            )
    }

    fun registreListener() {
        sensorManager.registerListener(
            sensorEventListener,
            sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER
            ),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    fun unregistreListener() {
        sensorManager.unregisterListener(sensorEventListener)
    }

    fun draw(canvas: Canvas) {

        canvas.drawPaint(backGroundPaint)

        for (item in drawablePlatforms) {
            item.draw(canvas)
        }

        target.draw(canvas)

        for (let in lets) {
            let.draw(canvas)
        }

        ball.draw(canvas, context.resources)
    }


    fun update() {
        ball.update(xAccel, yAccel, drawablePlatforms)

        for (let in lets) {
            if (!view.gameOver && let.checkIntercept(ball, 1.1)) {
                view.finisTheGame(R.string.lose, lvl)
                break
            }
        }

        if (!view.gameOver && target.checkIntercept(ball, 0.65)) {
            view.finisTheGame(R.string.win, lvl)
            lvl++
        }
    }
}

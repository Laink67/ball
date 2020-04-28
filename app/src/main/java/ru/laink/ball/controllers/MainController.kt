package ru.laink.ball.controllers

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import ru.laink.ball.models.CannonBall
import ru.laink.ball.models.Let
import ru.laink.ball.models.Platform
import ru.laink.ball.other.Constant.Companion.HORIZONTAL_PLATFORM_HEIGHT
import ru.laink.ball.other.Constant.Companion.MAX_ACCEL
import ru.laink.ball.other.Constant.Companion.STARTING_VELOCITY
import ru.laink.ball.other.Constant.Companion.VERTICAL_PLATFORM_WIDTH
import ru.laink.ball.views.LabyrinthView
import kotlin.math.abs
import kotlin.math.sign
import java.util.*
import kotlin.collections.ArrayList

class MainController(
    private val context: Context
) {
    private var level = 0

    // Переменные для игрового цикла и отслеживания игры
    private var timeLeft = 0.0 // Оставшееся время в секунду
    var shotsFired = 0 // Количество сделанных выстрелов
    var totalElapsedTime = 0.0 // Затраченное время в секундах

    private var xAccel = 0.0f
    private var yAccel = 0.0f
    private var velocityX = STARTING_VELOCITY
    private var velocityY = STARTING_VELOCITY

    // Переменные Paint для рисования элементов на экране
    private var textPaint: Paint // Для вывода текста
    private var backGroundPaint: Paint // Для стирания области рисования

    val view = LabyrinthView(context, this)

    private var sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    lateinit var ball: CannonBall
    lateinit var target: ru.laink.ball.models.Target
    private lateinit var drawablePlatforms: ArrayList<Platform>
    private lateinit var lets: ArrayList<Let>

    private var rand: Random

    init {
        // Регистрация слушателя SurfaceHolder.CallBack для получения методов изменения состояния SurfaceView
        view.holder.addCallback(view)

        textPaint = Paint()
        backGroundPaint = Paint()
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

        createElementsFirstLvl()
//        timeLeft = 10.0 // Обратный отсчёт с 10 сек
//        shotsFired = 0 // Начальное количество выстрелов
//        totalElapsedTime = 0.0 // Обнулить затраченное время
    }


    private fun generatePlatformsFirstLvl() {
        drawablePlatforms = arrayListOf(
            Platform(
                Color.GRAY,
                view.screenWidth / 3,
                0,
                VERTICAL_PLATFORM_WIDTH,
                (view.screenHeight * 0.7).toInt()
            ), Platform(
                Color.GRAY,
                view.screenWidth / 2,
                view.screenHeight / 2,
                VERTICAL_PLATFORM_WIDTH,
                view.screenHeight
            ),
            Platform(
                Color.GRAY,
                (view.screenWidth * 0.7).toInt(),
                0,
                VERTICAL_PLATFORM_WIDTH,
                (view.screenHeight * 0.7).toInt()
            ), Platform(
                Color.GRAY,
                (view.screenWidth * 0.85).toInt(),
                (view.screenHeight * 0.2).toInt(),
                (view.screenWidth * 0.5).toInt(),
                HORIZONTAL_PLATFORM_HEIGHT
            ),
            Platform(Color.GRAY,
                0,
                (view.screenHeight * 0.2).toInt(),
                (view.screenWidth*0.2).toInt(),
                HORIZONTAL_PLATFORM_HEIGHT
                )
        )
    }

    private fun createElementsFirstLvl() {
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
            view,
            Color.GREEN,
            0,
            0,
            radius
        )

        // Создание платформ
        generatePlatformsFirstLvl()

        lets =
            arrayListOf(
                Let(
                    Color.BLACK, (view.screenWidth * 0.72).toInt(),
                    (view.screenHeight * 0.3).toInt(),
                    radius
                ),
                Let(Color.BLACK,
                    view.screenWidth / 2 - VERTICAL_PLATFORM_WIDTH,
                    (view.screenHeight * 0.37).toInt(),
                    radius
                    ),
                Let(Color.BLACK,
                    view.screenWidth / 2 - VERTICAL_PLATFORM_WIDTH,
                    0,
                    radius
                    ),
                Let(Color.BLACK,
                    view.screenWidth / 3 - 2*VERTICAL_PLATFORM_WIDTH,
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

    fun draw(canvas: Canvas, resources: Resources) {
        // Очистка фона
        canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), backGroundPaint)


        for (item in drawablePlatforms) {
            item.draw(canvas)
        }

        target.draw(canvas)

        for (let in lets) {
            let.draw(canvas)
        }

        ball.draw(canvas, resources)
    }


    fun update() {
        ball.update(xAccel, yAccel, drawablePlatforms)

        if (target.checkIntercept(ball, 0.35)) {
            view.stopGame()
        }


        for (let in lets) {
            if (let.checkIntercept(ball, 1.1)) {
                view.finisTheGame()
                break
            }
        }

    }
}

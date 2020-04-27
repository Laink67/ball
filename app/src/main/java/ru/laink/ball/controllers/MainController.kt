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
import ru.laink.ball.views.LabyrinthView
import java.util.*

class MainController(
    private val context: Context
) {
    // Переменные размеров
//    var screenWidth: Int = 0
//    var screenHeight: Int = 0

    lateinit var targets: ArrayList<Target>

    // Переменные для игрового цикла и отслеживания игры
    private var timeLeft = 0.0 // Оставшееся время в секунду
    var shotsFired = 0 // Количество сделанных выстрелов
    var totalElapsedTime = 0.0 // Затраченное время в секундах

    private var xAccel = 0.0f
    private var yAccel = 0.0f
    private var velocityX = 50f /*xAccel * frameTime*/
    private var velocityY = 50f /*yAccel * frameTime*/

    // Переменные Paint для рисования элементов на экране
    private var textPaint: Paint // Для вывода текста
    private var backGroundPaint: Paint // Для стирания области рисования

    val view = LabyrinthView(context, this)

    private var sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    lateinit var ball: CannonBall
    lateinit var target: ru.laink.ball.models.Target
    private lateinit var drawablePlatforms: LinkedList<Platform>
    private lateinit var lets: ArrayList<Let>

    private var rand: Random

    init {
        // Ссылка на MainActivity

        // Регистрация слушателя SurfaceHolder.CallBack для получения методов изменения состояния SurfaceView
        view.holder.addCallback(view)

        textPaint = Paint()
        backGroundPaint = Paint()
        backGroundPaint.color = Color.WHITE

        rand = Random()
    }


    private lateinit var sensorEventListener: SensorEventListener

    fun newGame() {
        val radius = ((3.0 / 50) * view.getSurfaceHeight()).toInt()
        val radiusBall = ((3.0 / 60) * view.getSurfaceHeight()).toInt()

        // Создание нового ball
        ball = CannonBall(
            view,
            Color.BLACK,
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

        drawablePlatforms = LinkedList()
        generatePlatforms()

        lets =
            arrayListOf(Let(Color.BLACK, view.screenWidth - 200, 400, radius))

//        targets = ArrayList()// Построение нового списка
//        createEnemies(1.0) // Добавление targets
//
//        timeLeft = 10.0 // Обратный отсчёт с 10 сек
//        shotsFired = 0 // Начальное количество выстрелов
//        totalElapsedTime = 0.0 // Обнулить затраченное время
    }

    private fun generatePlatforms() {
        var platform = Platform(
            Color.GRAY,
            view.screenWidth / 3,
            0,
            30,
            /*view.screenHeight / 2*/(view.screenHeight * 0.7).toInt()
        )
        this.drawablePlatforms.add(platform)

        platform =
            Platform(
                Color.GRAY,
                view.screenWidth / 2,
                view.screenHeight / 2,
                30,
                view.screenHeight
            )
        this.drawablePlatforms.add(platform)

        platform =
            Platform(
                Color.GRAY,
                (view.screenWidth * 0.7).toInt(),
                0,
                30,
                (view.screenHeight * 0.7).toInt()
            )
        this.drawablePlatforms.add(platform)

        platform = Platform(
            Color.GRAY,
            (view.screenWidth * 0.85).toInt(),
            (view.screenHeight * 0.2).toInt(),
            (view.screenWidth * 0.5).toInt(),
            30
        )
        this.drawablePlatforms.add(platform)
    }

    fun registreListener() {
        sensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

            override fun onSensorChanged(event: SensorEvent?) {
                if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    xAccel = event.values[1]
                    yAccel = -event.values[0] // don't know why we have to negate the y value...
                    update()
                }
            }
        }

        sensorManager.registerListener(
            sensorEventListener,
            sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER
            ),
            SensorManager.SENSOR_DELAY_NORMAL
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

//        // Вывод оставшегося времени
//        canvas.drawText(
//            resources.getString(R.string.time_remaining_format, timeLeft),
//            50f,
//            100f,
//            textPaint
//        )
//
//        cannon.draw(canvas) // Рисование пушки
//
//        // Рисование игровых элементов
//        if (cannon.cannonBall != null && cannon.cannonBall!!.onScreen)
//            cannon.cannonBall?.draw(canvas)
//
//        // Рисование всех мишеней
//        for (target in targets) {
//            target.draw(canvas)
//            target.drawPolitic(
//                canvas,
//                context.resources
//            )
//        }

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
/*
        if (lets[0].checkIntercept(ball,1.1)) {
            view.finisTheGame()
        }
*/

    }
}

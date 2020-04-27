package ru.laink.ball.views

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.core.content.contentValuesOf
import androidx.fragment.app.FragmentActivity
import ru.laink.ball.GameOverDialog
import ru.laink.ball.R
import ru.laink.ball.controllers.MainController

class LabyrinthView(context: Context?, private val controller: MainController) :
    SurfaceView(context),
    SurfaceHolder.Callback {

    private var activity: Activity = context as Activity // Для отображения окна в потоке GUI

    // Переменные размеров
    var screenWidth: Int = 0
    var screenHeight: Int = 0

    lateinit var gameThread: GameThread // Управляет циклом игры
    private var gameOver = false
    var dialogIsDisplayed = false

    init {
        // Регистрация слушателя SurfaceHolder.CallBack для получения методов изменения состояния SurfaceView
        holder.addCallback(this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        screenHeight = h
        screenWidth = w
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {}

    // Вызывается при уничтожении поверхности
    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        // Обеспечить корректную зависимость потока
        var retry = true
        gameThread.setRunning(false) // Завершение потока

        while (retry) {
            try {
                gameThread.join() // Ожидать завершение потока
                retry = false
            } catch (e: InterruptedException) {
                Log.e(ContentValues.TAG, "Thread interrupted", e)
            }
        }
    }

    private fun hideSystemsBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE
    }

    fun newGame() {
        controller.newGame() // Создание новой игры

        if (gameOver) {
            gameOver = false
            gameThread = GameThread(holder) // Создать поток
            gameThread.setRunning(true)
            gameThread.start() // Запуск потока игрового цикла
        }

        hideSystemsBars()
        controller.registreListener()
    }

    // Вызывается при создании поверхности
    override fun surfaceCreated(p0: SurfaceHolder?) {
        if (!dialogIsDisplayed) {
            newGame()

            gameThread = GameThread(holder) // Создание потока
            gameThread.setRunning(true) // Запуск игры
            gameThread.start() // Запуск потока игрового цикла
        }
    }

    fun finisTheGame() {
        controller.unregistreListener()
        gameOver = true
        gameThread.setRunning(false) // Приказывем потоку завершиться
        showGameOverDialog(R.string.lose) // Сообщение о проигрыше
    }

    fun stopGame() {
        gameThread.setRunning(false)// Приказывем потоку завершиться
    }

    fun getSurfaceHeight(): Int {
        return this.holder.surfaceFrame.height()
    }

    fun drawGameElements(canvas: Canvas) {
        controller.draw(canvas, resources)
    }

    // Вывод системной панели и панели приложения
    private fun showSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    private fun showGameOverDialog(messageId: Int) {
        // Объект DialogFragment для вывода статистики и начала новой игры
        val gameResult = GameOverDialog(this, messageId)

        // В UI-потоке FragmentManager используется для вывода DialogFragment

        activity.runOnUiThread {
            showSystemBars() // Выход из режима погружения
            dialogIsDisplayed = true
            gameResult.isCancelable = false // Модальное окно
            gameResult.show((activity as FragmentActivity).supportFragmentManager, "results")
        }
    }


    // Многократно вызывается Thread для обновления
    inner class GameThread(holder: SurfaceHolder) : Thread() {

        private var surfaceHolder = holder
        private var threadIsRunning = true

        // Изменение состояния выполнения
        fun setRunning(running: Boolean) {
            threadIsRunning = running
        }

        // Управление игровым циклом
        override fun run() {
            var canvas: Canvas? = null // Используется для рисования
            var previousFrameTime = System.currentTimeMillis()
            var num: Long = System.currentTimeMillis()
            var speedIncrease = 1.0

            while (threadIsRunning) {
                try {
                    // Получение Canvas  для монопольного рисования из этого потока
                    canvas = surfaceHolder.lockCanvas(null)
                    screenWidth = canvas.width
                    // Блокировка surfaceHolder для рисования
                    synchronized(surfaceHolder) {
                        controller.update()// Обновление состояния игры
//                        testForCollisions() // Проверка столкновений с GameElement
                        drawGameElements(canvas) // Рисование на объекте canvas
//                        previousFrameTime = currentTime
                    }
                } finally {
                    // Вывести содержимое canvas на CannonView  и разрешить использовать canvas другим потокам
                    if (canvas != null)
                        surfaceHolder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }
}
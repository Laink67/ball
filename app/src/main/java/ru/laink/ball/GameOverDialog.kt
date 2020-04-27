package ru.laink.ball

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.laink.ball.views.LabyrinthView

class GameOverDialog(
    private val labyrinthView: LabyrinthView,
    private val messageId: Int
) : DialogFragment() {

    // Объект DialogFragment для вывода статистики и начала новой игры
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Создание диалогового окна c выводом строки messageId\
        val builder = AlertDialog.Builder(activity)
            .setTitle(resources.getString(messageId))
            // Вывод количество выстрелов и затраченного времени
            .setMessage(
                R.string.results_format
            )

        builder.setPositiveButton(
            R.string.reset_game
        ) { _, _ ->
            labyrinthView.dialogIsDisplayed = false
            labyrinthView.newGame() // Создани и начало новой партии
        }

        return builder.create()
    }
}
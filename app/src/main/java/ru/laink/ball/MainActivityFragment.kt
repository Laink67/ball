package ru.laink.ball

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_1lvl.*
import ru.laink.ball.R
import ru.laink.ball.controllers.MainController
import ru.laink.ball.views.LabyrinthView

class MainActivityFragment : Fragment() {

    private lateinit var mainController: MainController
    private lateinit var labyrinthView: LabyrinthView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainController = MainController(this.context!!)
        labyrinthView = mainController.view
        return labyrinthView
    }

    // Пауза MainActivity завершает игру
    override fun onPause() {
        super.onPause()
        labyrinthView.stopGame()
    }
}
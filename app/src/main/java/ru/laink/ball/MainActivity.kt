package ru.laink.ball

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

//    private val controller = MainController(context = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

/*
    override fun onResume() {
        controller.registreListener()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        controller.unregistreListener()
    }
*/
}

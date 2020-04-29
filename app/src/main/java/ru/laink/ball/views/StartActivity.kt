package ru.laink.ball.views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.transition.TransitionManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.start_activity.*
import ru.laink.ball.MainActivity
import ru.laink.ball.R

class StartActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var constraintSet: ConstraintSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        handler = Handler()
        constraintLayout = findViewById(R.id.start_activity)
        constraintSet = ConstraintSet()

        constraintSet.clone(this, R.layout.start_activity2)

        button_start.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        button_exit.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        handler.postDelayed({
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(constraintLayout)
            }
            (constraintSet).applyTo(constraintLayout)
        }, 1000)
    }

}
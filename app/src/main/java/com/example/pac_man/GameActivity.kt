package com.example.pac_man

import android.graphics.PixelFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GameActivity: AppCompatActivity() {

    lateinit var gameView: GameView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameView = findViewById(R.id.gameView)

    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }

}

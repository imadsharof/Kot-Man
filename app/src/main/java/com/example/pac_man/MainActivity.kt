package com.example.pac_man

import android.content.Intent
import android.graphics.PixelFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var playButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        playButton = findViewById(R.id.button)


        playButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, GameActivity::class.java))
        }

        //gameView.setZOrderOnTop(false)
        //gameView.holder.setFormat(PixelFormat.TRANSPARENT) : ligne qui permet de mettre canvas
        // transparent sur un fond

    }

}
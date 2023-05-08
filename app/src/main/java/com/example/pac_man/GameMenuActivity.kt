package com.example.pac_man

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class GameMenuActivity : AppCompatActivity() {
    lateinit var playButton : ImageButton
    private var soundPool: SoundPool? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        playButton = findViewById<ImageButton>(R.id.button)

        soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        val soundplay = soundPool!!.load(baseContext, R.raw.startgameaudio, 1)

        Thread {
            while (true) {
                runOnUiThread {
                    if (playButton.rotationY != 90F) {
                        playButton.rotationY += 1
                    }
                    else{
                        playButton.rotationY = -90F
                    }
                }
                Thread.sleep(15)
            }
        }.start()

        val animation = playButton.background as AnimationDrawable
        animation.start()



        playButton.setOnClickListener{
            soundPool?.play(soundplay, 1F, 1F, 0, 0, 1F)
            startActivity(Intent(this@GameMenuActivity, GameModeActivity::class.java))
        }


    }

}
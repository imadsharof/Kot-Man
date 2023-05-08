package com.example.pac_man

import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GameModeActivity : AppCompatActivity() {
    lateinit var arcadeButton : Button
    lateinit var facileButton : Button
    lateinit var normalButton : Button
    lateinit var difficileButton : Button

    private var soundPool: SoundPool? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gamemode)
        arcadeButton = findViewById<Button>(R.id.arcadeButton)
        facileButton = findViewById<Button>(R.id.facileButton)
        normalButton = findViewById<Button>(R.id.normalButton)
        difficileButton = findViewById<Button>(R.id.difficileButton)

        //gameView = findViewById<GameView>(R.id.gameView)
        

        soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        val soundplay = soundPool!!.load(baseContext, R.raw.startgameaudio, 1)


        arcadeButton.setOnClickListener{
            soundPool?.play(soundplay, 1F, 1F, 0, 0, 1F)
            val intent = Intent(this@GameModeActivity, MainActivity::class.java)
            intent.putExtra("gameMode", "arcade")
            startActivity(intent)
        }

        facileButton.setOnClickListener{
            soundPool?.play(soundplay, 1F, 1F, 0, 0, 1F)
            val intent = Intent(this@GameModeActivity, MainActivity::class.java)
            intent.putExtra("gameMode", "facile")
            startActivity(intent)
        }

        normalButton.setOnClickListener{
            soundPool?.play(soundplay, 1F, 1F, 0, 0, 1F)
            val intent = Intent(this@GameModeActivity, MainActivity::class.java)
            intent.putExtra("gameMode", "normal")
            startActivity(intent)
        }

        difficileButton.setOnClickListener{
            soundPool?.play(soundplay, 1F, 1F, 0, 0, 1F)
            val intent = Intent(this@GameModeActivity, MainActivity::class.java)
            intent.putExtra("gameMode", "difficile")
            startActivity(intent)
        }


    }

}
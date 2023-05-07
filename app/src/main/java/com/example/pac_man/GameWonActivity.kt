package com.example.pac_man

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameWonActivity : AppCompatActivity() {

    lateinit var scoretext: TextView
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gamewin)

        val score = intent.getIntExtra("score", 0)

        scoretext = findViewById(R.id.scorefinal)
        scoretext.setText("Your score : $score")

        mediaPlayer = MediaPlayer.create(this, R.raw.youwin)
        mediaPlayer.start()

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
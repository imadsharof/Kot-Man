package com.example.pac_man

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class GameWonActivity : AppCompatActivity() {

    lateinit var scoretext: TextView
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var restartButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gamewin)

        val score = intent.getIntExtra("score", 0)

        scoretext = findViewById(R.id.scorefinal)
        scoretext.setText("Your score : $score")

        mediaPlayer = MediaPlayer.create(this, R.raw.youwin)
        mediaPlayer.start()

        // Lorsque le jeu se termine, envoyez un signal pour arrêter la musique
        val gameWonIntent = Intent("ACTION_GAME_WIN")
        LocalBroadcastManager.getInstance(this).sendBroadcast(gameWonIntent)

        restartButton = findViewById<ImageButton>(R.id.restart_button)

        Thread {
            while (true) {
                runOnUiThread {
                    if (restartButton.rotationY != 90F) {
                        restartButton.rotationY += 1
                    }
                    else{
                        restartButton.rotationY = -90F
                    }
                }
                Thread.sleep(15)
            }
        }.start()


        restartButton.setOnClickListener {
            // Arrêter la musique
            mediaPlayer.stop()
            mediaPlayer.reset()
            // Lancer l'activité principale (ou l'activité de jeu) pour redémarrer le jeu
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)

            // Fermer l'activité GameOver
            finish()
        }

    }

    override fun onDestroy() {
        // Libérer les ressources du MediaPlayer lorsque l'activité est détruite
        mediaPlayer.release()
        super.onDestroy()
    }
}
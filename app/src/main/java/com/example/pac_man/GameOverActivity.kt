package com.example.pac_man

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.media.MediaPlayer
import android.widget.ImageButton
import android.widget.TextView


class GameOverActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var restartButton :ImageButton
    lateinit var scoretext: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gameover)

        // Récupérez le score à partir de l'intent
        val score = intent.getIntExtra("score", 0)

        scoretext= findViewById(R.id.monscore)
        scoretext.setText("Your score : $score")

        mediaPlayer = MediaPlayer.create(this, R.raw.gameover)
        mediaPlayer.start()

        mediaPlayer = MediaPlayer.create(this, R.raw.pacmancry)
        mediaPlayer.start()


        // Trouver le bouton "Restart" dans le fichier de mise en page et configurer un OnClickListener
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
            val gameMenuActivityIntent = Intent(this, GameMenuActivity::class.java)
            startActivity(gameMenuActivityIntent)

            // Fermer l'activité GameOver
            finish()
        }

        // Lorsque le jeu se termine, envoyez un signal pour arrêter la musique
        val gameOverIntent = Intent("ACTION_GAME_OVER")
        LocalBroadcastManager.getInstance(this).sendBroadcast(gameOverIntent)

    }

    override fun onDestroy() {
        super.onDestroy()

        // Libérer les ressources du MediaPlayer lorsque l'activité est détruite
        mediaPlayer.release()
    }
}
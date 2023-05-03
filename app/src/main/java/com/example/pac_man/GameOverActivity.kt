package com.example.pac_man

import android.graphics.PixelFormat
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.media.MediaPlayer


class GameOverActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gameover)

        mediaPlayer = MediaPlayer.create(this, R.raw.gameover)
        mediaPlayer.start()

        mediaPlayer = MediaPlayer.create(this, R.raw.pacmancry)
        mediaPlayer.start()


        // Trouver le bouton "Restart" dans le fichier de mise en page et configurer un OnClickListener
        val restartButton = findViewById<Button>(R.id.restart_button)
        restartButton.setOnClickListener {
            // Lancer l'activité principale (ou l'activité de jeu) pour redémarrer le jeu
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)

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
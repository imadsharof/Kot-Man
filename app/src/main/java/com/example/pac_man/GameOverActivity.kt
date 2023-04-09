package com.example.pac_man

import android.graphics.PixelFormat
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GameOverActivity : AppCompatActivity() {

    private lateinit var life: Life
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gameover)

        // Trouver le bouton "Restart" dans le fichier de mise en page et configurer un OnClickListener
        val restartButton = findViewById<Button>(R.id.restart_button)
        restartButton.setOnClickListener {
            // Lancer l'activité principale (ou l'activité de jeu) pour redémarrer le jeu
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)

            // Fermer l'activité GameOver
            finish()
        }
    }
}
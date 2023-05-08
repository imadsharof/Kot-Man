package com.example.pac_man

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity: AppCompatActivity() {

    lateinit var gameView: GameView

    private lateinit var compteARebours: ImageView



    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var gameOverReceiver: BroadcastReceiver
    private lateinit var gameWonReceiver: BroadcastReceiver
    private lateinit var gameRestartReceiver: BroadcastReceiver

    private var gameMode: String? = null

    private val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameView = findViewById(R.id.gameView)


        val intent = intent
        val previousScore = intent.getIntExtra("score", 0)
        val previousLives = intent.getIntExtra("lives", 3)
        gameView.previousScore = previousScore
        gameView.previousLives = previousLives

        gameMode = intent.getStringExtra("gameMode") ?: "normal"
        if (previousScore>10){gameMode = "arcade"}
        val tempGameMode = gameMode
        if (tempGameMode != null) {
            gameView.modeDeJeu = tempGameMode
        }
        gameView.initializeObjects()



        mediaPlayer = MediaPlayer.create(this, R.raw.gameaudio)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        compteARebours = findViewById(R.id.compte_a_rebours)
        (compteARebours.drawable as AnimationDrawable).start()

        handler.postDelayed({
            compteARebours.visibility = View.GONE
            gameView.startGame()
            //gameView.bonus[0].resetTime()
        }, 6000)


        gameOverReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                stopMusic()
            }
        }

        gameWonReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                stopMusic()
            }
        }

        gameRestartReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                stopMusic()
            }
        }

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(gameOverReceiver, IntentFilter("ACTION_GAME_OVER"))


        LocalBroadcastManager.getInstance(this)
        .registerReceiver(gameWonReceiver, IntentFilter("ACTION_GAME_WIN"))

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(gameRestartReceiver, IntentFilter("ACTION_GAME_RESTART"))
}

    private val startGameRunnable = Runnable {
        gameView.startGame() // Ajoutez cette méthode à votre classe GameView
    }

    fun stopMusic() {
        mediaPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer.release()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(gameOverReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(gameWonReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(gameRestartReceiver)


        handler.removeCallbacks(startGameRunnable)
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

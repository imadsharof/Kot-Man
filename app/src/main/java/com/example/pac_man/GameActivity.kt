package com.example.pac_man

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class GameActivity: AppCompatActivity() {

    lateinit var gameView: GameView

    private lateinit var compteARebours: ImageView

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var gameOverReceiver: BroadcastReceiver
    private lateinit var gameWonReceiver: BroadcastReceiver


    private val startGameRunnable = Runnable {
        gameView.startGame() // Ajoutez cette méthode à votre classe GameView
    }
    private val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameView = findViewById(R.id.gameView)



        mediaPlayer = MediaPlayer.create(this, R.raw.gameaudio)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        compteARebours = findViewById(R.id.compte_a_rebours)
        (compteARebours.drawable as AnimationDrawable).start()

        handler.postDelayed({
            compteARebours.visibility = View.GONE
            gameView.startGame()
            gameView.bonus[0].resetTime()
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

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(gameOverReceiver, IntentFilter("ACTION_GAME_OVER"))


        LocalBroadcastManager.getInstance(this)
        .registerReceiver(gameWonReceiver, IntentFilter("ACTION_GAME_WIN"))
}

    fun stopMusic() {
        mediaPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer.release()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(gameOverReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(gameWonReceiver)

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

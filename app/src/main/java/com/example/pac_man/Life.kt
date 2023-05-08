package com.example.pac_man

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*

class Life(private val resources: Resources, private val screenWidth: Float, private val currentActivity: Activity, private val score: Score) {
    private val lifeBitmap: Bitmap
    private val textPaint: Paint
    private val lifeSpacing: Int = 10 // Espace entre les images de vies
    var lives: Int = 20
    //lateinit var score: Score
    init {
        val lifeOriginal = BitmapFactory.decodeResource(resources, R.drawable.kotlindroite)
        lifeBitmap = Bitmap.createScaledBitmap(lifeOriginal, 50, 50, true)

        textPaint = Paint()
        textPaint.color = Color.WHITE
        textPaint.textSize = 50f
        textPaint.typeface = Typeface.DEFAULT_BOLD
    }

    fun getRemainingLives(): Int {
        return lives
    }

    fun decreaseLife() {
        lives -= 1
        if (lives <= 0) {
            val gameOverIntent = Intent(currentActivity, GameOverActivity::class.java)
            gameOverIntent.putExtra("score", score.score) // Ajoutez le score en tant qu'extra
            currentActivity.startActivity(gameOverIntent)
        }
    }

    fun increaseLife(){
        lives +=1
    }

    fun draw(canvas: Canvas) {
        canvas.drawText("Life", (screenWidth - lifeBitmap.width * lives - (lives - 1) * lifeSpacing - 110).toFloat(), 60f, textPaint)

        for (i in 0 until lives) {
            canvas.drawBitmap(lifeBitmap, (screenWidth - lifeBitmap.width * (i + 1) - i * lifeSpacing).toFloat(), 10f, null)
        }
    }
}
package com.example.pac_man

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface

class Score(private val context: Context, private val screenWidth: Float) {
    private val paint = Paint().apply {
        textSize = 60f
        typeface = Typeface.DEFAULT_BOLD
        color = Color.WHITE
    }
    private var score = 0

    fun draw(canvas: Canvas) {
        val scoreText = "Score: $score"
        val textWidth = paint.measureText(scoreText)
        val x = (screenWidth - textWidth) / 2
        val y = 60f
        canvas.drawText(scoreText, x, y, paint)
    }

    fun incrementScore(points: Int) {
        score += points
    }
}
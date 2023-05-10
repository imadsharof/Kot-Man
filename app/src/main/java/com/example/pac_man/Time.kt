package com.example.pac_man

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint


class Time {

    private val paint = Paint().apply {
        color = Color.WHITE
        textSize = 50f
        isAntiAlias = true
        // Charger la police personnalisée
    }

    fun draw(canvas: Canvas, elapsedTime: Long) {
        val seconds = elapsedTime / 1000
        val minutes = seconds / 60
        val formattedTime = String.format("%02d:%02d", minutes, seconds % 60)
        canvas.drawText("Timer: $formattedTime", 20f, 60f, paint)
    }
}
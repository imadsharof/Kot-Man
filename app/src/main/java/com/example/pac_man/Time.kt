package com.example.pac_man

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint


class Time {

    //  initialiser la variable startTime, ce qui permet d'obtenir l'heure actuelle en millisecondes
    //temps initial sera de 6 secondes pour permettre le compte à rebours de s'afficher
    private var startTime = System.currentTimeMillis() + 6000

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
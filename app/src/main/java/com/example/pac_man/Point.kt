package com.example.pac_man

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint

abstract class Point( val caseWidth: Float, val caseHeight: Float, val labyrinthe: Labyrinthe) {
    protected val paint = Paint()

    abstract fun draw(canvas: Canvas)
}
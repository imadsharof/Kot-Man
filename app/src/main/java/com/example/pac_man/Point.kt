package com.example.pac_man

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint

abstract class Point(private val resources: Resources, val caseWidth: Float, val caseHeight: Float) {
    protected val paint = Paint()

    abstract fun draw(canvas: Canvas, map: Array<IntArray>)
}
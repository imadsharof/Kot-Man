package com.example.pac_man
import android.graphics.Canvas

interface TimeDisplay {
    fun drawTime(canvas: Canvas, elapsedTime: Long)
    fun resetTime()
}
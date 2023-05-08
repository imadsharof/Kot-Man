package com.example.pac_man

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas

abstract class Bonus(
    private val resources: Resources,
    val caseWidth: Float,
    val caseHeight: Float,
    bonusDrawable: Int,
    private val bonusFrequency : Int // A chaque bonusfrequency minute le bonus apparaît,

) {
    val bonusBitmap: Bitmap


    var tileX: Float = 0F
    var tileY: Float = 0F

    var startTime = System.currentTimeMillis()

    var isVisible: Boolean = false
    var isCollected: Boolean = false

    var timeBonus = 60 * bonusFrequency


    init {
        val bonusOriginal = BitmapFactory.decodeResource(resources, bonusDrawable)
        bonusBitmap =
            Bitmap.createScaledBitmap(bonusOriginal, caseWidth.toInt(), caseHeight.toInt(), true)
        //resetTime()
    }

    fun resetTime() {
        startTime = System.currentTimeMillis()
    }

    open fun draw(canvas: Canvas,labyrinthe: Labyrinthe) {
        if (isVisible && !isCollected) {
            canvas.drawBitmap(bonusBitmap, tileX * caseWidth, tileY * caseHeight, null)
        }
    }

    open fun spawnBonus() {
        if (!isVisible && !isCollected) {
            tileX = 12F
            tileY = 10F
            isVisible = true
            isCollected = false
        }
    }

    open fun hideBonus() {
        isVisible = false
    }


    open fun update() {
        val elapsedTime = System.currentTimeMillis() - startTime
        val elapsedSeconds = elapsedTime / 1000

        // Le bonus apparait de 00:30 sec à 00:40 puis disparait et revient chaque apres timeBonus minute
        if (elapsedSeconds % timeBonus >= 30 && elapsedSeconds % timeBonus < 40) {
            if (!isCollected) {
                spawnBonus()
            }

            // Le bonus disparait de 00:40 à jusque (1:30 + timeBonus ) et revient apres chaque timeBonus minute
        } else if (elapsedSeconds % timeBonus >= 40 ) {
            isCollected = false
            isVisible = false

            // Avant les 30 sec le bonus n'apparait pas
        } else {
            hideBonus()
        }

    }
}
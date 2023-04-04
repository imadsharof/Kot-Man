package com.example.pac_man

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.SurfaceView

class PacMan(
    private val resources: Resources,
    private val caseWidth: Int,
    private val caseHeight: Int,
    private val screenWidth: Int,
    private val screenHeight: Int
) {
    private val pacManBitmap: Bitmap
    var posX: Int = 0
    var posY: Int = 0
    var direction: Direction = Direction.NONE

    init {
        val pacManOriginal = BitmapFactory.decodeResource(resources, R.drawable.kotlinlogo)
        pacManBitmap = Bitmap.createScaledBitmap(pacManOriginal, caseWidth, caseHeight, true)
    }

    enum class Direction {
        NONE,
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    fun spawnPacMan() {
        // Initialise la position de Pac-Man dans le labyrinthe
        posX = 13 * caseWidth
        posY = 17 * caseHeight
    }

    fun update(labyrinthe: Labyrinthe) {
        when (direction) {
            Direction.NONE -> {
                // Ne bouge pas
            }
            Direction.UP -> {
                if (!labyrinthe.isMur((posX / caseWidth), (posY / caseHeight) - 1)) {
                    posY -= caseHeight
                }
            }
            Direction.DOWN -> {
                if (!labyrinthe.isMur((posX / caseWidth), (posY / caseHeight) + 1)) {
                    posY += caseHeight
                }
            }
            Direction.LEFT -> {
                if (!labyrinthe.isMur((posX / caseWidth) - 1, (posY / caseHeight))) {
                    posX -= caseWidth
                }
            }
            Direction.RIGHT -> {
                if (!labyrinthe.isMur((posX / caseWidth) + 1, (posY / caseHeight))) {
                    posX += caseWidth
                }
            }
        }
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(pacManBitmap, posX.toFloat(), posY.toFloat(), null)
    }
}


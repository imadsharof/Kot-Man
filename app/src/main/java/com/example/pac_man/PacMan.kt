package com.example.pac_man

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.util.Log
import android.view.SurfaceView
import androidx.core.content.ContextCompat.startActivity

import java.lang.Math.abs


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
    private lateinit var fantome: Fantome



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


    fun update(labyrinthe: Labyrinthe, score: Score,fantomes: List<Fantome>,life: Life) {
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


        val pointsGained = eatPoint(labyrinthe)
        score.incrementScore(pointsGained)
        teleport(labyrinthe)
        checkCollisionsWithGhosts(fantomes, life)


    }

    fun teleport(labyrinthe: Labyrinthe) {
        val i = posY / caseHeight
        val j = posX / caseWidth
        if (labyrinthe.map[i][j] == 6) {
            // Trouve la position de la case 7
            for (x in labyrinthe.map.indices) {
                for (y in labyrinthe.map[x].indices) {
                    if (labyrinthe.map[x][y] == 7) {
                        posX = y * caseWidth
                        posY = x * caseHeight
                        return
                    }
                }
            }
        } else if (labyrinthe.map[i][j] == 7) {
            // Trouve la position de la case 6
            for (x in labyrinthe.map.indices) {
                for (y in labyrinthe.map[x].indices) {
                    if (labyrinthe.map[x][y] == 6) {
                        posX = y * caseWidth
                        posY = x * caseHeight
                        return
                    }
                }
            }
        }

    }

    private fun checkCollisionsWithGhosts(fantomes: List<Fantome>, life: Life) {
        for (fantome in fantomes) {
            if (abs(posX - fantome.posX) <= caseWidth / 4 && abs(posY - fantome.posY) <= caseHeight / 4) {
                life.decreaseLife()
            }

        }
    }




    fun draw(canvas: Canvas) {
        canvas.drawBitmap(pacManBitmap, posX.toFloat(), posY.toFloat(), null)
    }

    fun eatPoint(labyrinthe: Labyrinthe): Int {
        val i = posY / caseHeight
        val j = posX / caseWidth

        if (labyrinthe.map[i][j] == 2 || labyrinthe.map[i][j] == 4) {
            labyrinthe.map[i][j] = 0
            return 10
        } else {
            return 0
        }
    }

}


package com.example.pac_man

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import kotlin.random.Random
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class Fantome(
    private val resources: Resources,
    val caseWidth: Int,
    val caseHeight: Int,
    fantomeDrawable: Int
) {
    private val fantomeBitmap: Bitmap
    var posX: Int = 0
    var posY: Int = 0

    private val scale = 1 // Ajustez ce facteur de mise à l'échelle selon vos besoins
    private val newWidth = (caseWidth * scale).toInt()
    private val newHeight = (caseHeight * scale).toInt()

    var direction: PacMan.Direction = PacMan.Direction.NONE

    private val moveJob = Job()
    private val moveScope = CoroutineScope(Dispatchers.Main + moveJob)
    init {
        val fantomeOriginal = BitmapFactory.decodeResource(resources, fantomeDrawable) // Ajoutez votre image de fantôme ici
        fantomeBitmap = Bitmap.createScaledBitmap(fantomeOriginal, newWidth, newHeight, true)
    }

    open fun spawnFantome() {
        // Initialise la position du fantôme dans le labyrinthe
        posX = 11 * caseWidth
        posY = 11 * caseHeight
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(fantomeBitmap, posX.toFloat(), posY.toFloat(), null)
    }

    fun startMoving(map: Array<IntArray>) {
        moveScope.launch {
            while (true) {
                moveRandomly(map)
                kotlinx.coroutines.delay(2000) // Contrôle la vitesse de déplacement du fantôme
            }
        }
    }

    fun stopMoving() {
        moveJob.cancel()
    }

    fun moveRandomly(map: Array<IntArray>) {
        val possibleDirections = mutableListOf<PacMan.Direction>()

        if (posX - caseWidth >= 0 && map[posY / caseHeight][(posX - caseWidth) / caseWidth] != 1 && map[posY / caseHeight][(posX - caseWidth) / caseWidth] != 5 && direction != PacMan.Direction.RIGHT) {
            possibleDirections.add(PacMan.Direction.LEFT)
        }
        if (posX + caseWidth < map[0].size * caseWidth && map[posY / caseHeight][(posX + caseWidth) / caseWidth] != 1 && map[posY / caseHeight][(posX + caseWidth) / caseWidth] != 5 && direction != PacMan.Direction.LEFT) {
            possibleDirections.add(PacMan.Direction.RIGHT)
        }
        if (posY - caseHeight >= 0 && map[(posY - caseHeight) / caseHeight][posX / caseWidth] != 1 && map[(posY - caseHeight) / caseHeight][posX / caseWidth] != 5 && direction != PacMan.Direction.DOWN) {
            possibleDirections.add(PacMan.Direction.UP)
        }
        if (posY + caseHeight < map.size * caseHeight && map[(posY + caseHeight) / caseHeight][posX / caseWidth] != 1 && map[(posY + caseHeight) / caseHeight][posX / caseWidth] != 5 && direction != PacMan.Direction.UP) {
            possibleDirections.add(PacMan.Direction.DOWN)
        }

        if (possibleDirections.isNotEmpty()) {
            val randomDirection = possibleDirections[Random.nextInt(possibleDirections.size)]

            when (randomDirection) {
                PacMan.Direction.LEFT -> {
                    posX -= caseWidth
                    direction = PacMan.Direction.LEFT
                }
                PacMan.Direction.RIGHT -> {
                    posX += caseWidth
                    direction = PacMan.Direction.RIGHT
                }
                PacMan.Direction.UP -> {
                    posY -= caseHeight
                    direction = PacMan.Direction.UP
                }
                PacMan.Direction.DOWN -> {
                    posY += caseHeight
                    direction = PacMan.Direction.DOWN
                }
                else -> { /* Ne rien faire */ }
            }
        }
    }
}
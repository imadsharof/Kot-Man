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
import kotlin.math.ceil

open class Fantome(
    private val resources: Resources,
    val caseWidth: Float,
    val caseHeight: Float,
    fantomeDrawable: Int
) {
    private val fantomeBitmap: Bitmap

    var tileX : Float = 0F
    var tileY : Float = 0F

    var nextTileX = 0F
    var nextTileY = 0F



    val speed = 5 // La vitesse de déplacement des fantômes

    private val scale = 1 // Ajustez ce facteur de mise à l'échelle selon vos besoins

    var direction: PacMan.Direction = PacMan.Direction.NONE

    private var updateCounter = 0
    private val updateThreshold = 20

    init {
        val fantomeOriginal = BitmapFactory.decodeResource(resources, fantomeDrawable) // Ajoutez votre image de fantôme ici
        fantomeBitmap = Bitmap.createScaledBitmap(fantomeOriginal, caseWidth.toInt(),  caseHeight.toInt(), true)
    }

    open fun spawnFantome() {
        // Initialise la position du fantôme dans le labyrinthe
        tileX = 11F
        tileY = 11F
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(fantomeBitmap, tileX*caseWidth, tileY*caseHeight, null)
    }



    fun moveRandomly(labyrinthe: Labyrinthe) {

        val possibleDirections = mutableListOf<PacMan.Direction>()

        if (!labyrinthe.isMur2(tileX - 1f, tileY) && direction != PacMan.Direction.RIGHT) {
            possibleDirections.add(PacMan.Direction.LEFT)
        }
        if (!labyrinthe.isMur2(tileX + 1f, tileY) && direction != PacMan.Direction.LEFT) {
            possibleDirections.add(PacMan.Direction.RIGHT)
        }
        if (!labyrinthe.isMur2(tileX, tileY - 1f) && direction != PacMan.Direction.DOWN) {
            possibleDirections.add(PacMan.Direction.UP)
        }
        if (!labyrinthe.isMur2(tileX, tileY + 1f) && direction != PacMan.Direction.UP) {
            possibleDirections.add(PacMan.Direction.DOWN)
        }

        if (possibleDirections.isNotEmpty()) {
            val randomDirection = possibleDirections[Random.nextInt(possibleDirections.size)]

            when (randomDirection) {
                PacMan.Direction.LEFT -> {
                    nextTileX = tileX - (1 / 16F)
                    if (!labyrinthe.isMur2(nextTileX, tileY) && !labyrinthe.isMur2(ceil(tileX - 1F), tileY)) {
                        tileX = nextTileX
                        direction = PacMan.Direction.LEFT
                    }
                }
                PacMan.Direction.RIGHT -> {
                    if (!labyrinthe.isMur2(tileX + 1F, tileY)) {
                        nextTileX = tileX + (1 / 16F)
                    }
                    if (!labyrinthe.isMur2(nextTileX, tileY) && !labyrinthe.isMur2(tileX + 1F, tileY)) {
                        tileX = nextTileX
                        direction = PacMan.Direction.RIGHT
                    }
                }
                PacMan.Direction.UP -> {
                    nextTileY = tileY - (1 / 16F)
                    if (!labyrinthe.isMur2(tileX, nextTileY) && !labyrinthe.isMur2(tileX, ceil(tileY - 1F))) {
                        tileY = nextTileY
                        direction = PacMan.Direction.UP
                    }
                }
                PacMan.Direction.DOWN -> {
                    nextTileY = tileY + (1 / 16F)
                    if (!labyrinthe.isMur2(tileX, nextTileY) && !labyrinthe.isMur2(tileX, tileY + 1F)) {
                        tileY = nextTileY
                        direction = PacMan.Direction.DOWN
                    }
                }
                else -> { /* Ne rien faire */
                }
            }
        }
    }
}

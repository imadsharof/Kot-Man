package com.example.pac_man

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.SurfaceView

class PacMan(private val resources: Resources, val caseWidth: Int, val caseHeight: Int){

    private val pacManBitmap: Bitmap
    private var posX: Int = 0 // Coordonnée X de la position du Pac-Man
    private var posY: Int = 0 // Coordonnée Y de la position du Pac-Man
    var dead = true

    init {
        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.kotlinlogo)
        pacManBitmap = Bitmap.createScaledBitmap(originalBitmap, caseWidth, caseHeight, true)
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(pacManBitmap, posX.toFloat(), posY.toFloat(), null)
    }

    fun setPosition(x: Int, y: Int) {
        posX = x * caseWidth
        posY = y * caseHeight
    }
}
    // code mis en commentaire
    /*private val paint = Paint()
    private var currentFrame = 0
    private var direction = 0 // 0: droite, 1: gauche

    private val pacmanFrames = arrayOfNulls<Bitmap>(6)

    init {
        pacmanFrames[0] = BitmapFactory.decodeResource(surfaceView.resources, R.drawable.pacman_droite_ouvregrand_bouche)
        pacmanFrames[1] = BitmapFactory.decodeResource(surfaceView.resources, R.drawable.pacman_droite_ouvre_bouche)
        pacmanFrames[2] = BitmapFactory.decodeResource(surfaceView.resources, R.drawable.pacman_ferme_bouche)
        pacmanFrames[3] = BitmapFactory.decodeResource(surfaceView.resources, R.drawable.pacman_gauche_ouvregrand_bouche)
        pacmanFrames[4] = BitmapFactory.decodeResource(surfaceView.resources, R.drawable.pacman_gauche_ouvre_bouche)
        pacmanFrames[5] = BitmapFactory.decodeResource(surfaceView.resources, R.drawable.pacman_ferme_bouche)
    }

    fun draw(canvas: Canvas, x: Float, y: Float) {
        canvas.drawBitmap(pacmanFrames[currentFrame]!!, x, y, paint)
    }

    fun update(animationSpeed: Long) {
        currentFrame = (currentFrame + 1) % 3 + direction * 3
    }

    fun changeDirection(newDirection: Int) {
        direction = newDirection
    }*/

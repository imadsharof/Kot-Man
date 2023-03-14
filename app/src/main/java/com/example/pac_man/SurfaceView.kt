package com.example.pac_man

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class SurfaceView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private val surfaceHolder: SurfaceHolder = holder
    private val mur: Mur = Mur(this)
    private val pacman: PacMan = PacMan(this)
    var pacmanX = 100f
    var pacmanY = 100f
    private val scheduledExecutorService: ScheduledExecutorService = Executors.newScheduledThreadPool(1)

        init {
        surfaceHolder.addCallback(this)
    }
    fun updatePacmanPosition(x: Float, y: Float, direction: Int) {
        pacmanX = x
        pacmanY = y
        pacman.changeDirection(direction)
    }
    override fun surfaceCreated(holder: SurfaceHolder) {
        val canvas = surfaceHolder.lockCanvas()
        if (canvas != null) {
           // mur.dessiner(canvas)
            pacman.draw(canvas, pacmanX, pacmanY)
            surfaceHolder.unlockCanvasAndPost(canvas)
        }

        scheduledExecutorService.scheduleAtFixedRate({ updateAndDraw() }, 0, 100, TimeUnit.MILLISECONDS)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        scheduledExecutorService.shutdown()
    }

    private fun updateAndDraw() {
        val canvas = surfaceHolder.lockCanvas()
        if (canvas != null) {
            // Mettre à jour la position et l'animation du Pacman
            pacman.update(100) // 100 ms pour la vitesse d'animation
            pacman.draw(canvas, pacmanX, pacmanY)

            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }
}
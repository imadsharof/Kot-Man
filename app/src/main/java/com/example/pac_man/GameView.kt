package com.example.pac_man

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView


class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), Runnable, SurfaceHolder.Callback {

    lateinit var canvas : Canvas

    private lateinit var thread : Thread
    private var drawing = false

    /*private lateinit var labyrinthe: Labyrinthe
    private lateinit var pacMan: PacMan*/


    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels
    private val caseWidth = screenWidth / 25
    private val caseHeight = screenHeight / 27

    private val pacMan: PacMan
    private val labyrinthe: Labyrinthe

    init {
        labyrinthe = Labyrinthe(resources, caseWidth, caseHeight)
        pacMan = PacMan(resources, caseWidth, caseHeight)
        labyrinthe.spawnPacMan(pacMan) // Initialise la position de Pac-Man dans le labyrinthe
    }



    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            pacMan.draw(canvas)
            labyrinthe.draw(canvas)


            holder.unlockCanvasAndPost(canvas)
        }
    }


    override fun run() {
        while(drawing) {
            draw()
        }
    }


    fun pause() {
        drawing = false
        thread.join()
    }

    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        TODO("Not yet implemented")
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        TODO("Not yet implemented")
    }

    }

package com.example.pac_man


import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Math.abs


class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), Runnable, SurfaceHolder.Callback  {

    lateinit var canvas : Canvas
    private lateinit var thread : Thread
    private var drawing = false

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    private val caseWidth = screenWidth / 25
    private val caseHeight = screenHeight / 27

    private val pacMan: PacMan
    private val labyrinthe: Labyrinthe
    private var pointGris: PointGris
    private var pointBonus : PointBonus

    private val timeDisplay: TimeDisplay = DrawTime(resources)
    private var startTime = System.currentTimeMillis()

    private val score = Score(context, screenWidth)

    private var initialX = 0f
    private var initialY = 0f

    val fantomeVert = FantomeVert(resources, caseWidth, caseHeight)
    val fantomeRouge = FantomeRouge(resources, caseWidth, caseHeight)
    val fantomeBleu = FantomeBleu(resources, caseWidth, caseHeight)
    val fantomeJaune = FantomeJaune(resources, caseWidth, caseHeight)

    val fantomes = arrayListOf(fantomeVert, fantomeRouge, fantomeBleu, fantomeJaune)



    init {
        labyrinthe = Labyrinthe(resources, caseWidth, caseHeight)
        pacMan = PacMan(resources, caseWidth, caseHeight,screenWidth,screenHeight)
        pacMan.spawnPacMan() // Initialise la position de Pac-Man dans le labyrinthe
        pointGris = PointGris(resources, caseWidth, caseHeight)
        pointBonus = PointBonus(resources, caseWidth, caseHeight)// Initialise l'instance de Point.

        for ( i in 0 until 4) {
            fantomes[i].spawnFantome()
            fantomes[i].startMoving(labyrinthe.map)
        }
    }



    override fun onTouchEvent(event: MotionEvent): Boolean {
        val currentX = event.x
        val currentY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = currentX
                initialY = currentY
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = currentX - initialX
                val deltaY = currentY - initialY

                if (abs(deltaX) > abs(deltaY)) {
                    // Déplacement horizontal
                    if (deltaX > 0) {
                        // Déplacement vers la droite
                        pacMan.direction = PacMan.Direction.RIGHT
                    } else {
                        // Déplacement vers la gauche
                        pacMan.direction = PacMan.Direction.LEFT
                    }
                } else {
                    // Déplacement vertical
                    if (deltaY > 0) {
                        // Déplacement vers le bas
                        pacMan.direction = PacMan.Direction.DOWN
                    } else {
                        // Déplacement vers le haut
                        pacMan.direction = PacMan.Direction.UP
                    }
                }
            }
        }
        return true
    }

    private fun clearCanvas() {
        val backgroundColor = Color.BLACK
        val paint = Paint().apply {
            color = backgroundColor
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, screenWidth.toFloat(), screenHeight.toFloat(), paint)
    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            clearCanvas() // Ajoutez cette ligne pour effacer le canvas
            labyrinthe.draw(canvas)
            pacMan.draw(canvas)
            pointGris.draw(canvas, labyrinthe.map)
            pointBonus.draw(canvas, labyrinthe.map)

            for ( i in 0 until 4) { fantomes[i].draw(canvas)}

            score.draw(canvas)

            val elapsedTime = System.currentTimeMillis() - startTime
            timeDisplay.drawTime(canvas, elapsedTime)


            holder.unlockCanvasAndPost(canvas)
        }
    }

    override fun run() {
        while(drawing) {
            pacMan.update(labyrinthe,score)

            for ( i in 0 until 4) { fantomes[i].moveRandomly(labyrinthe.map)}
            draw()
            Thread.sleep(300) // Contrôle la vitesse de déplacement de Pac-Man

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

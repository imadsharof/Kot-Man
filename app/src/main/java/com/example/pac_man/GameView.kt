package com.example.pac_man


import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Math.abs
import kotlin.math.round


class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), Runnable, SurfaceHolder.Callback {

    lateinit var canvas : Canvas
    private lateinit var thread : Thread
    private var drawing = false

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels.toFloat()
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels.toFloat()

    private val caseWidth : Float = screenWidth / 25
    private val caseHeight : Float = screenHeight / 27

    private val pacMan: PacMan
    private val labyrinthe: Labyrinthe
    private val life: Life
    private var pointGris: PointGris
    private var pointBonus : PointBonus

    private val timeDisplay: TimeDisplay = DrawTime(resources)
    private var startTime = System.currentTimeMillis() + 6000

    private val score = Score(context, screenWidth)

    private var initialX = 0f
    private var initialY = 0f

    val fantomeVert = FantomeVert(resources, caseWidth, caseHeight)
    val fantomeRouge = FantomeRouge(resources, caseWidth, caseHeight)
    val fantomeBleu = FantomeBleu(resources, caseWidth, caseHeight)
    val fantomeJaune = FantomeJaune(resources, caseWidth, caseHeight)

    val fantomes = arrayListOf(fantomeVert, fantomeRouge, fantomeBleu, fantomeJaune)

    private var gameStarted = false

    private var lastDirection: PacMan.Direction = PacMan.Direction.NONE
    init {
        val activity = context as Activity

        labyrinthe = Labyrinthe(resources, caseWidth, caseHeight)
        pacMan = PacMan(resources,caseWidth, caseHeight,labyrinthe)
        pacMan.spawnPacMan() // Initialise la position de Pac-Man dans le labyrinthe
        pointGris = PointGris(resources, caseWidth, caseHeight)
        pointBonus = PointBonus(resources, caseWidth, caseHeight)// Initialise l'instance de Point.
        life = Life(resources, screenWidth,activity)

        for ( i in 0 until 4) {
            fantomes[i].spawnFantome()
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

                // Permettre a mon pacman les comebacks, gauche droite ou haut bas
                val isOppositeDirection = when (lastDirection) {
                    PacMan.Direction.LEFT -> abs(deltaX) > abs(deltaY) && deltaX > 0
                    PacMan.Direction.RIGHT -> abs(deltaX) > abs(deltaY) && deltaX < 0
                    PacMan.Direction.UP -> abs(deltaY) > abs(deltaX) && deltaY > 0
                    PacMan.Direction.DOWN -> abs(deltaY) > abs(deltaX) && deltaY < 0
                    else -> false
                }

                val epsilon = 0.001f
                // Vérifie si Pac-Man est sur une case de la matrice
                val isOnTileX = abs(pacMan.tileX - round(pacMan.tileX)) == 0F
                val isOnTileY = abs(pacMan.tileY - round(pacMan.tileY)) == 0F

                //val epsilonX = abs(pacMan.tileX/round(pacMan.tileX) - 1F)
                //val epsilonY = abs(pacMan.tileY/round(pacMan.tileY) - 1F)

                if ( isOppositeDirection || (isOnTileX && isOnTileY)) {
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
                    lastDirection = pacMan.direction

                    initialX = currentX
                    initialY = currentY
                }

            }
        }
        return true
    }

    fun startGame() {
        gameStarted = true
        // Effectuez ici toute initialisation supplémentaire dont vous avez besoin pour démarrer la partie
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
            life.draw(canvas)

            val elapsedTime = System.currentTimeMillis() - startTime
            timeDisplay.drawTime(canvas, elapsedTime)


            holder.unlockCanvasAndPost(canvas)
        }
    }

   private fun update() {
        pacMan.update(labyrinthe, score,fantomes,life)
        for ( i in 0 until 4) {
            fantomes[i].moveRandomly(labyrinthe)
        }
    }


    override fun run() {
        while (drawing) {
            if (!holder.surface.isValid) {
                continue
            }

            if (gameStarted) {
                // Calculez le temps écoulé depuis la dernière mise à jour

                // Mettez à jour la position de Pac-Man en fonction du temps écoulé
                update()
                //pacMan.checkCollisionsWithGhosts(fantomes, life)

                // Dessinez le labyrinthe et les autres éléments
                draw()
            }

        }
    }
    //Thread.sleep(200) // Contrôle la vitesse de déplacement
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

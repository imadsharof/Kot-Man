package com.example.pac_man


import android.app.Activity
import android.content.Context
import android.content.Intent
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
    private val currentActivity: Activity


    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels.toFloat()
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels.toFloat()

    private val caseWidth : Float = screenWidth / 25
    private val caseHeight : Float = screenHeight / 27

    private val pacMan: PacMan
    private val labyrinthe: Labyrinthe
    private val life: Life
    private var pointPetit: PointPetit
    private var pointGros : PointGros
    private val score : Score

    private val timeDisplay: TimeDisplay = Time(resources)
    private var startTime = System.currentTimeMillis() + 6000

    //val score = Score(context, screenWidth)


    private var initialX = 0f
    private var initialY = 0f


    val fantomeVert = FantomeVert(resources, caseWidth, caseHeight)
    val fantomeRouge = FantomeRouge(resources, caseWidth, caseHeight)
    val fantomeBleu = FantomeBleu(resources, caseWidth, caseHeight)
    val fantomeJaune = FantomeJaune(resources, caseWidth, caseHeight)

    val fantomes = arrayListOf(fantomeVert, fantomeRouge, fantomeBleu, fantomeJaune)

    val bonusCoeur = BonusCoeur(resources, caseWidth, caseHeight)
    val bonusCafe = BonusCafe(resources, caseWidth, caseHeight)
    val bonus = arrayListOf(bonusCoeur,bonusCafe)

    private var gameStarted = false

    private var lastDirection: PacMan.Direction = PacMan.Direction.NONE
    init {
        currentActivity = context as Activity

        labyrinthe = Labyrinthe(resources, caseWidth, caseHeight)
        pacMan = PacMan(resources,caseWidth, caseHeight,labyrinthe)
        pacMan.spawnPacMan() // Initialise la position de Pac-Man dans le labyrinthe
        pointPetit = PointPetit(resources, caseWidth, caseHeight)
        pointGros = PointGros(resources, caseWidth, caseHeight)// Initialise l'instance de Point.
        score = Score(context,screenWidth)
        life = Life(resources, screenWidth,currentActivity,score)
        val minDistance = 0.5f

        for ( i in 0 until fantomes.size) {
            fantomes[i].spawnFantome()
        }
        for ( bonuss in bonus){
        bonuss.spawnBonus()}
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

                // Vérifie si Pac-Man est sur une case de la matrice

                //isOnTileX = abs(pacMan.tileX - round(pacMan.tileX)) == 0F
                //isOnTileY = abs(pacMan.tileY - round(pacMan.tileY)) == 0F


                if ( isOppositeDirection || (pacMan.isOnTileX && pacMan.isOnTileY)) {

                    if (abs(deltaX) > abs(deltaY)) {
                        println("deltaX: $deltaX, deltaY: $deltaY, isOppositeDirection: $isOppositeDirection, isOnTileX: ${pacMan.isOnTileX}, isOnTileY: ${pacMan.isOnTileY}")
                        // Déplacement horizontal
                        if (deltaX > 0) {
                            // Déplacement vers la droite
                            pacMan.direction = PacMan.Direction.RIGHT
                        } else {
                            // Déplacement vers la gauche
                            pacMan.direction = PacMan.Direction.LEFT
                        }
                    }else if (abs(deltaY) > abs(deltaX) ) {
                        println("deltaX: $deltaX, deltaY: $deltaY, isOppositeDirection: $isOppositeDirection, isOnTileX: ${pacMan.isOnTileX}, isOnTileY: ${pacMan.isOnTileY}")
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

    fun partieGagnee(): Boolean {
        val chiffresRecherches = setOf(2, 4, 7, 12, 13, 16, 17, 18)

        for (ligne in labyrinthe.map) {
            for (chiffre in ligne) {
                if (chiffre in chiffresRecherches) {
                    return false
                }
            }
        }
        return true
    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            clearCanvas() // Ajoutez cette ligne pour effacer le canvas
            labyrinthe.draw(canvas)
            pacMan.draw(canvas)
            pointPetit.draw(canvas, labyrinthe.map)
            pointGros.draw(canvas, labyrinthe.map)

            for ( i in 0 until 4) { fantomes[i].draw(canvas)}
            score.draw(canvas)
            life.draw(canvas)
            for ( bonuss in bonus){ bonuss.draw(canvas)}

            val elapsedTime = System.currentTimeMillis() - startTime
            timeDisplay.drawTime(canvas, elapsedTime)


            holder.unlockCanvasAndPost(canvas)
        }
    }

   private fun update() {
        pacMan.update(labyrinthe, score,fantomes,life,bonus,this)
        for ( i in 0 until 4) {
            fantomes[i].moveRandomly(labyrinthe)
        }
       for ( bonuss in bonus){
           bonuss.update()
       }
       if(partieGagnee()){
           val gameWonIntent = Intent(currentActivity, GameWonActivity::class.java)
           gameWonIntent.putExtra("score", score.score)
           currentActivity.startActivity(gameWonIntent)
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

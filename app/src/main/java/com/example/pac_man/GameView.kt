package com.example.pac_man


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.lang.Math.abs


class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), Runnable, SurfaceHolder.Callback {

    var modeDeJeu: String = "facile"
    var isInitialized = false

    lateinit var canvas : Canvas
    private lateinit var thread : Thread
    private var drawing = false


    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels.toFloat()
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels.toFloat()

    var previousScore = 0
    var previousLives = 0

    private lateinit var pacMan: PacMan
    private lateinit var labyrinthe: Labyrinthe
    private lateinit var life: Life

    private lateinit var pointPetit: PointPetit
    private lateinit var pointGros : PointGros
    private lateinit var point : List<out Point>
    private lateinit var score : Score


    private lateinit var fantomeVert : FantomeVert
    private lateinit var fantomeRouge : FantomeRouge
    private lateinit var fantomeJaune : FantomeJaune
    private lateinit var fantomeBleu : FantomeBleu

    private lateinit var fantome: List<MutableList<out Fantome>>


    private lateinit var listFantomeVert: MutableList<FantomeVert>
    private lateinit var listFantomeRouge: MutableList<FantomeRouge>
    private lateinit var listFantomeJaune: MutableList<FantomeJaune>
    private lateinit var listFantomeBleu: MutableList<FantomeBleu>

    private lateinit var bonus: List<out Bonus>

    private lateinit var bonusCoeur : BonusCoeur
    private lateinit var bonusCafe : BonusCafe
    private lateinit var bonusSwift : BonusSwift


    private lateinit var time : Time
    private var startTime = System.currentTimeMillis() + 6000


    private var initialX = 0f
    private var initialY = 0f


    var caseWidth : Float = screenWidth / 25 // 25 , 44 ou 67
    var caseHeight : Float = screenHeight / 27


    val currentActivity = context as Activity

    init {
        initializeObjects()
    }
    fun initializeObjects() {
        initializeCaseDimensions()

        labyrinthe = Labyrinthe(resources, caseWidth, caseHeight, modeDeJeu)

        score = Score(screenWidth)
        life = Life(resources, screenWidth,currentActivity)
        time = Time()

        fantomeVert = FantomeVert(resources, caseWidth, caseHeight,labyrinthe)
        fantomeRouge = FantomeRouge(resources, caseWidth, caseHeight,labyrinthe)
        fantomeBleu = FantomeBleu(resources, caseWidth, caseHeight,labyrinthe)
        fantomeJaune = FantomeJaune(resources, caseWidth, caseHeight,labyrinthe)

        listFantomeVert = mutableListOf<FantomeVert>()
        listFantomeRouge = mutableListOf<FantomeRouge>()
        listFantomeBleu = mutableListOf<FantomeBleu>()
        listFantomeJaune = mutableListOf<FantomeJaune>()

        fantome = listOf(listFantomeVert, listFantomeRouge, listFantomeBleu, listFantomeJaune)

        pointPetit = PointPetit(resources, caseWidth, caseHeight,labyrinthe)
        pointGros = PointGros(resources, caseWidth, caseHeight,labyrinthe)

        point = listOf(pointPetit,pointGros)


        bonusCoeur = BonusCoeur(resources, caseWidth,caseHeight,labyrinthe)
        bonusCafe = BonusCafe(resources,  caseWidth,caseHeight,labyrinthe)
        bonusSwift = BonusSwift(resources,  caseWidth,caseHeight,labyrinthe)

        bonus = listOf(bonusCoeur, bonusCafe, bonusSwift)

        pacMan = PacMan(resources, caseWidth, caseHeight, labyrinthe, score, fantome, life, bonus)
        pacMan.spawnPacMan()


        for (y in labyrinthe.map.indices) {
            for (x in labyrinthe.map[y].indices) {
                if (labyrinthe.map[y][x] == 40) {
                    val fantomeBleu = FantomeBleu(resources, caseWidth, caseHeight,labyrinthe)
                    fantomeBleu.setPosition(x.toFloat(), y.toFloat())
                    listFantomeBleu.add(fantomeBleu)
                }
                else if (labyrinthe.map[y][x] == 15) {
                    val fantomeJaune = FantomeJaune(resources, caseWidth, caseHeight,labyrinthe)
                    fantomeJaune.setPosition(x.toFloat(), y.toFloat())
                    listFantomeJaune.add(fantomeJaune)
                }

                else if (labyrinthe.map[y][x] == 41) {
                    val fantomeRouge = FantomeRouge(resources, caseWidth, caseHeight,labyrinthe)
                    fantomeRouge.setPosition(x.toFloat(), y.toFloat())
                    listFantomeRouge.add(fantomeRouge)
                }

                else if (labyrinthe.map[y][x] == 42) {
                    val fantomeVert = FantomeVert(resources, caseWidth, caseHeight,labyrinthe)
                    fantomeVert.setPosition(x.toFloat(), y.toFloat())
                    listFantomeVert.add(fantomeVert)
                }

            }
        }

        val lesfantomes = arrayListOf(fantomeVert, fantomeRouge, fantomeBleu, fantomeJaune)
        for (fantomes in lesfantomes){fantomes.spawnFantome()}

        val bonusi = arrayListOf(bonusCoeur,bonusCafe,bonusSwift)
        for ( bonuss in bonusi){ bonuss.spawnBonus()}

        isInitialized = true

        if(previousLives != 0 && previousScore !=0 ){
            life.lives = previousLives
            score.score = previousScore
        }

    }

    private var gameStarted = false

    private var lastDirection: PacMan.Direction = PacMan.Direction.NONE


    fun initializeCaseDimensions() {
        when (modeDeJeu) {
            "arcade" -> {
                // Modifiez les valeurs en fonction de vos besoins
                caseWidth = screenWidth / 25
                caseHeight = screenHeight / 27
            }
            "facile" -> {
                // Modifiez les valeurs en fonction de vos besoins
                caseWidth = screenWidth / 25
                caseHeight = screenHeight / 27
            }
            "normal" -> {
                // Modifiez les valeurs en fonction de vos besoins
                caseWidth = screenWidth / 44
                caseHeight = screenHeight / 27
            }
            "difficile" -> {
                // Modifiez les valeurs en fonction de vos besoins
                caseWidth = screenWidth / 67
                caseHeight = screenHeight / 27
            }
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

                // Vérifie si Pac-Man est sur une case de la matrice

                //isOnTileX = abs(pacMan.tileX - round(pacMan.tileX)) == 0F
                //isOnTileY = abs(pacMan.tileY - round(pacMan.tileY)) == 0F


                if ( isOppositeDirection || (pacMan.isOnTileX && pacMan.isOnTileY)) {

                    if (abs(deltaX) > abs(deltaY)) {
                        //println("deltaX: $deltaX, deltaY: $deltaY, isOppositeDirection: $isOppositeDirection, isOnTileX: ${pacMan.isOnTileX}, isOnTileY: ${pacMan.isOnTileY}")
                        // Déplacement horizontal
                        if (deltaX > 0) {
                            // Déplacement vers la droite
                            pacMan.direction = PacMan.Direction.RIGHT
                        } else {
                            // Déplacement vers la gauche
                            pacMan.direction = PacMan.Direction.LEFT
                        }
                    }else if (abs(deltaY) > abs(deltaX) ) {
                        //println("deltaX: $deltaX, deltaY: $deltaY, isOppositeDirection: $isOppositeDirection, isOnTileX: ${pacMan.isOnTileX}, isOnTileY: ${pacMan.isOnTileY}")
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
            val bonus = arrayListOf(bonusCoeur,bonusCafe,bonusSwift)
            val allFantomes = listOf(listFantomeVert, listFantomeRouge, listFantomeBleu, listFantomeJaune)
            if (holder.surface.isValid) {
                canvas = holder.lockCanvas()

                clearCanvas()
                labyrinthe.draw(canvas)
                pacMan.draw(canvas)
                for ( points in point) {points.draw(canvas)}
                for (fantomeList in allFantomes) {
                    for (fantome in fantomeList) {
                        fantome.draw(canvas)
                    }
                }
                score.draw(canvas)
                life.draw(canvas)
                for (bonuss in bonus) {
                    bonuss.draw(canvas)
                }

                val elapsedTime = System.currentTimeMillis() - startTime
                time.draw(canvas, elapsedTime)


                holder.unlockCanvasAndPost(canvas)
            }
    }

   private fun update() {
           val bonus = arrayListOf(bonusCoeur,bonusCafe,bonusSwift)
           val allFantomes = listOf(listFantomeVert, listFantomeRouge, listFantomeBleu, listFantomeJaune)
           pacMan.update()
           for (fantomeList in allFantomes) {
               for (fantome in fantomeList) {
                   fantome.moveRandomly()
               }
           }

           for (bonuss in bonus) {
               bonuss.update()
           }
           if (partieGagnee()) {
               if(modeDeJeu != "arcade"){
               val gameWonIntent = Intent(currentActivity, GameWonActivity::class.java)
               gameWonIntent.putExtra("score", score.score)
               currentActivity.startActivity(gameWonIntent)}
               else{
                   val mainActivityIntent = Intent(currentActivity, MainActivity::class.java)
                   mainActivityIntent.putExtra("score", score.score)
                   mainActivityIntent.putExtra("lives", life.lives)
                   mainActivityIntent.putExtra("modeDeJeu", "arcade")
                   currentActivity.startActivity(mainActivityIntent)

                   // Envoie de message
                   val restartMusicIntent = Intent("ACTION_GAME_RESTART")
                   LocalBroadcastManager.getInstance(context).sendBroadcast(restartMusicIntent)
               }
           }
           println(modeDeJeu)

    }


    override fun run() {
            while (drawing) {
                if (!holder.surface.isValid) {
                    continue
                }

                if (gameStarted) {

                    update()
                    //pacMan.checkCollisionsWithGhosts(fantomes, life)

                    // Dessinez le labyrinthe et les autres éléments
                    draw()
                }

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
        resume()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        //
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

        pause()
    }

    }

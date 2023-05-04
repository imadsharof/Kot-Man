package com.example.pac_man

import android.content.res.Resources
import android.graphics.*
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.math.ceil
import kotlin.math.floor


// Classe PacMan qui représente le personnage Pac-Man
// Elle suit les principes SRP et SR, car elle ne s'occupe que des actions liées à Pac-Man et ne contient que des fonctions qui sont directement liées à Pac-Man
// Elle suit également le principe OCP, car elle est facilement extensible pour de futures fonctionnalités liées à Pac-Man
class PacMan(
    private val resources: Resources, // Les ressources de l'application
    private val caseWidth: Float, // La largeur d'une case dans le labyrinthe
    private val caseHeight: Float, // La hauteur d'une case dans le labyrinthe
    private val labyrinthe: Labyrinthe
) {
    private val pacManBitmap: Bitmap // L'image de Pac-Man

    var tileX : Float = 0F
    var tileY : Float = 0F

    var nextTileX = 0F
    var nextTileY = 0F



    var speed = 5

    var lastUpdateTime: Long = 0

    private var updateCounter = 0
    private val updateThreshold = 20

    private var isMoving = false


    var direction: Direction = Direction.NONE // La direction actuelle de Pac-Man
    // Initialise la classe PacMan

    init {
        // Charge l'image de Pac-Man depuis les ressources de l'application
        val pacManOriginal = BitmapFactory.decodeResource(resources, R.drawable.kotlinlogo)
        // Redimensionne l'image de Pac-Man pour qu'elle ait la même taille qu'une case dans le labyrinthe
        pacManBitmap = Bitmap.createScaledBitmap(pacManOriginal, caseWidth.toInt(),
            caseHeight.toInt(), true)

    }

    // Énumération qui définit les directions possibles de Pac-Man
    enum class Direction {
        NONE,
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    fun setPosition(newtileX: Float, newtileY: Float) {
        tileX = newtileX
        tileY = newtileY
    }

    // Initialise la position de Pac-Man dans le labyrinthe
    fun spawnPacMan() {
        tileX = 13F
        tileY = 17F
    }

    /*fun isWall(labyrinthe: Labyrinthe, x: Float, y: Float): Boolean {
        val i = y / caseHeight
        val j = x / caseWidth
        return labyrinthe.map[i][j] == 1
    }*/

    fun moveLeft() {
    }

    fun moveRight() {

    }

    fun moveUp() {
    }

    fun moveDown() {

    }

    // Met à jour la position de Pac-Man en fonction de sa direction actuelle et de la carte du labyrinthe
    // et effectue des actions liées à la collision avec des points ou des fantômes
    // Suit le principe SRP, car elle contient des fonctions liées à la mise à jour de la position et de l'état de Pac-Man, qui sont des actions cohérentes
    // Elle suit également le principe OCP, car elle peut facilement être étendue pour des futures fonctionnalités liées à Pac-Man

    fun update(labyrinthe: Labyrinthe, score: Score, fantomes: List<Fantome>, life: Life) {


            when (direction) {
                Direction.NONE -> {
                    // Ne bouge pas
                }
                Direction.UP -> {
                    nextTileY = tileY - ( 1 / 16F)

                    if (!labyrinthe.isMur2(tileX, nextTileY)&& !labyrinthe.isMur2(tileX ,ceil(tileY - 1F)) ) {
                        tileY = nextTileY
                    }
                }
                Direction.DOWN -> {
                    nextTileY = tileY +( 1 / 16F)

                    if (!labyrinthe.isMur2(tileX, nextTileY) && !labyrinthe.isMur2(tileX ,tileY + 1F)) {
                        tileY = nextTileY
                    }
                }
                Direction.LEFT -> {
                    nextTileX = tileX - ( 1 / 16F)
                    if (!labyrinthe.isMur2(nextTileX, tileY) && !labyrinthe.isMur2(ceil(tileX - 1F),tileY)) {
                        tileX = nextTileX
                    }
                }
                Direction.RIGHT -> {
                    if(!labyrinthe.isMur2(tileX + 1F,tileY)){
                    nextTileX = tileX + ( 1 / 16F)}
                    if (!labyrinthe.isMur2(nextTileX, tileY) && !labyrinthe.isMur2(tileX + 1F,tileY)) {
                        tileX = nextTileX
                    }
                }
            }
            lastUpdateTime = System.currentTimeMillis()
            //adjustPacmanPosition(labyrinthe)
            if(labyrinthe.isIntersection(tileX,tileY,labyrinthe.map)){ println("INTERSECTION TROUVE")}
            // Récupère les points obtenus par Pac-Man en mangeant des points dans la carte du labyrinthe
            val pointsGained = eatPoint(labyrinthe)
            // Met à jour le score du joueur en ajoutant les points obtenus
            score.incrementScore(pointsGained)
            // Téléporte Pac-Man s'il se trouve sur une case de téléportation dans la carte du labyrinthe
            teleport(labyrinthe)
            // Vérifie s'il y a collision entre Pac-Man et les fantômes
            //checkCollisionsWithGhosts(fantomes, life)
        }


    /*fun adjustPacmanPosition(labyrinthe: Labyrinthe) {
        val currentTile = labyrinthe.getTile(posX / caseWidth, posY / caseHeight)
        println("Voici la currentTile" + currentTile)
        if ( currentTile == 7  || currentTile == 8) {
            setPosition(posX / caseWidth * caseWidth, posY / caseHeight * caseHeight)
        }
    }*/

    // Téléporte Pac-Man s'il se trouve sur une case de téléportation dans la carte du labyrinthe
// Suit le principe SRP, car elle contient une fonction cohérente qui est liée à la téléportation de Pac-Man
    fun teleport(labyrinthe: Labyrinthe) {
        if (labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 6 ) {
            println( "tileX" + floor(tileX) +"and tileY" +floor(tileY))
            setPosition(21F,12F)
        }
        else if( labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 9) {
            println( "tileX" + floor(tileX) +"and tileY" +floor(tileY))
            setPosition(3F,12F)
        }
    }
    // Vérifie s'il y a collision entre Pac-Man et les fantômes
// Suit le principe SRP, car elle contient une fonction cohérente qui est liée à la vérification des collisions entre Pac-Man et les fantômes
    /*fun checkCollisionsWithGhosts(fantomes: List<Fantome>, life: Life) {
        // Vérifier si Pac-Man touche n'importe quel fantôme dans la liste
        if (fantomes.any { fantome ->
                posX == fantome.posX && posY == fantome.posY
            }) {
            life.decreaseLife()
        }
    }*/



    // Dessine Pac-Man sur le canvas
    // Suit le principe SRP, car elle contient une fonction cohérente qui est liée au dessin de Pac-Man
    fun draw(canvas: Canvas) {
        canvas.drawBitmap(pacManBitmap, tileX* caseWidth, tileY * caseHeight, null)
    }


    // Mange les points présents sur la case actuelle de Pac-Man s'il y en a
    // Suit le principe SRP, car elle contient une fonction cohérente qui est liée à la consommation des points par Pac-Man
    fun eatPoint(labyrinthe: Labyrinthe): Int {
        if (labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 2 ||
            labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 4
        ) {

            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 0)
            return 10
        }
        else if(labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 7){
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 11)
            return 10
        }
        return 0
    }

   /* fun switchValue(labyrinthe: Labyrinthe, x: Int, y: Int) {
        if (labyrinthe.map[y][x] == 7) {
            labyrinthe.map[y][x] = 10
        } else if (labyrinthe.map[y][x] == 10) {
            labyrinthe.map[y][x] = 7
        }
    }*/

}


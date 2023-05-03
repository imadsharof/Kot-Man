package com.example.pac_man

import android.content.res.Resources
import android.graphics.*
import kotlin.math.abs
import kotlin.math.sqrt

// Classe PacMan qui représente le personnage Pac-Man
// Elle suit les principes SRP et SR, car elle ne s'occupe que des actions liées à Pac-Man et ne contient que des fonctions qui sont directement liées à Pac-Man
// Elle suit également le principe OCP, car elle est facilement extensible pour de futures fonctionnalités liées à Pac-Man
class PacMan(
    private val resources: Resources, // Les ressources de l'application
    private val caseWidth: Int, // La largeur d'une case dans le labyrinthe
    private val caseHeight: Int // La hauteur d'une case dans le labyrinthe
) {
    private val pacManBitmap: Bitmap // L'image de Pac-Man

    var posX: Int = 0 // La position x de Pac-Man sur l'écran
    var posY: Int = 0 // La position y de Pac-Man sur l'écran

    private val pacManRect = RectF()

    private val scale = 1
    val pacManWidth = (caseWidth * scale).toInt()
    val pacManHeight = (caseHeight * scale).toInt()

    var direction: Direction = Direction.NONE // La direction actuelle de Pac-Man
    private lateinit var fantome: Fantome // Le fantôme le plus proche de Pac-Man

    // Initialise la classe PacMan
    init {
        // Charge l'image de Pac-Man depuis les ressources de l'application
        val pacManOriginal = BitmapFactory.decodeResource(resources, R.drawable.kotlinlogo)
        // Redimensionne l'image de Pac-Man pour qu'elle ait la même taille qu'une case dans le labyrinthe
        pacManBitmap = Bitmap.createScaledBitmap(pacManOriginal, caseWidth, caseHeight, true)
    }

    // Énumération qui définit les directions possibles de Pac-Man
    enum class Direction {
        NONE,
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    // Initialise la position de Pac-Man dans le labyrinthe
    fun spawnPacMan() {
        posX = 13 * caseWidth
        posY = 17 * caseHeight
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
                if (!labyrinthe.isMur((posX / caseWidth), (posY / caseHeight) - 1)) {
                    posY -= caseHeight
                }
            }
            Direction.DOWN -> {
                if (!labyrinthe.isMur((posX / caseWidth), (posY / caseHeight) + 1)) {
                    posY += caseHeight
                }
            }
            Direction.LEFT -> {
                if (!labyrinthe.isMur((posX / caseWidth) - 1, (posY / caseHeight))) {
                    posX -= caseWidth
                }
            }
            Direction.RIGHT -> {
                if (!labyrinthe.isMur((posX / caseWidth) + 1, (posY / caseHeight))) {
                    posX += caseWidth
                }
            }
        }
        // Récupère les points obtenus par Pac-Man en mangeant des points dans la carte du labyrinthe
        val pointsGained = eatPoint(labyrinthe)
        // Met à jour le score du joueur en ajoutant les points obtenus
        score.incrementScore(pointsGained)
        // Téléporte Pac-Man s'il se trouve sur une case de téléportation dans la carte du labyrinthe
        teleport(labyrinthe)
        // Vérifie s'il y a collision entre Pac-Man et les fantômes
        checkCollisionsWithGhosts(fantomes, life)
    }

    // Téléporte Pac-Man s'il se trouve sur une case de téléportation dans la carte du labyrinthe
// Suit le principe SRP, car elle contient une fonction cohérente qui est liée à la téléportation de Pac-Man
    private fun teleport(labyrinthe: Labyrinthe) {
        val i = posY / caseHeight
        val j = posX / caseWidth
        if (labyrinthe.map[i][j] == 6) {
            // Trouve la position de la case 7 dans la carte du labyrinthe
            for (x in labyrinthe.map.indices) {
                for (y in labyrinthe.map[x].indices) {
                    if (labyrinthe.map[x][y] == 7) {
                        // Téléporte Pac-Man sur la case 7
                        posX = y * caseWidth
                        posY = x * caseHeight
                        return
                    }
                }
            }
        } else if (labyrinthe.map[i][j] == 7) {
            // Trouve la position de la case 6 dans la carte du labyrinthe
            for (x in labyrinthe.map.indices) {
                for (y in labyrinthe.map[x].indices) {
                    if (labyrinthe.map[x][y] == 6) {
                        // Téléporte Pac-Man sur la case 6
                        posX = y * caseWidth
                        posY = x * caseHeight
                        return
                    }
                }
            }
        }
    }
    // Vérifie s'il y a collision entre Pac-Man et les fantômes
// Suit le principe SRP, car elle contient une fonction cohérente qui est liée à la vérification des collisions entre Pac-Man et les fantômes
    private fun checkCollisionsWithGhosts(fantomes: List<Fantome>, life: Life) {
        val pacManRect = RectF(posX.toFloat(), posY.toFloat(), (posX + pacManWidth).toFloat(), (posY + pacManHeight).toFloat())

        for (fantome in fantomes) {
            val fantomeRect = RectF(fantome.posX.toFloat(), fantome.posY.toFloat(), (fantome.posX + fantome.newWidth).toFloat(), (fantome.posY + fantome.newHeight).toFloat())

            if (RectF.intersects(pacManRect, fantomeRect)) {
                // Réduit le nombre de vies du joueur en cas de collision avec un fantôme
                life.decreaseLife()
            }
        }
    }

    // Dessine Pac-Man sur le canvas
    // Suit le principe SRP, car elle contient une fonction cohérente qui est liée au dessin de Pac-Man
    fun draw(canvas: Canvas) {
        canvas.drawBitmap(pacManBitmap, posX.toFloat(), posY.toFloat(), null)
    }


    // Mange les points présents sur la case actuelle de Pac-Man s'il y en a
    // Suit le principe SRP, car elle contient une fonction cohérente qui est liée à la consommation des points par Pac-Man
    fun eatPoint(labyrinthe: Labyrinthe): Int {
        val i = posY / caseHeight
        val j = posX / caseWidth

        if (labyrinthe.map[i][j] == 2 || labyrinthe.map[i][j] == 4) {
            labyrinthe.map[i][j] = 0
            return 10
        } else {
            return 0
        }
    }

}


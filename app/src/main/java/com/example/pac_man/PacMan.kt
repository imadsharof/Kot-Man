package com.example.pac_man

import android.content.res.Resources
import android.graphics.*
import kotlin.math.*


// Classe PacMan qui représente le personnage Pac-Man
// Elle suit les principes SRP et SR, car elle ne s'occupe que des actions liées à Pac-Man et ne contient que des fonctions qui sont directement liées à Pac-Man
// Elle suit également le principe OCP, car elle est facilement extensible pour de futures fonctionnalités liées à Pac-Man
class PacMan(
    private val resources: Resources, // Les ressources de l'application
    private val caseWidth: Float, // La largeur d'une case dans le labyrinthe
    private val caseHeight: Float, // La hauteur d'une case dans le labyrinthe
    private val labyrinthe: Labyrinthe
) {
    private var pacManBitmap: Bitmap // L'image de Pac-Man

    private val pacManRightBitmap: Bitmap
    private val pacManLeftBitmap: Bitmap
    private val pacManUpBitmap: Bitmap
    private val pacManDownBitmap: Bitmap


    var tileX: Float = 0F
    var tileY: Float = 0F

    var isOnTileX = abs(tileX - round(tileX)) == 0F
    var isOnTileY = abs(tileY - round(tileY)) == 0F

    var nextTileX = 0F
    var nextTileY = 0F

    private var lastTileX = 0F
    private var lastTileY = 0F
    private var timeOnTile = 0L
    private val maxTimeOnTile = 1000 // 3 secondes en millisecondes


    var speed = 1 / 8F
    var eatFantome = false //si PacMan a mangé le fantome

    var lastUpdateTime: Long = 0

    var lastCollisionTime: Long = 0
    var lastEatTime: Long = 0
    var lastBonusTime: Long = 0
    var lastBonusTime2: Long = 0
    var lastPointGrosTime: Long = 0


    var direction: Direction = Direction.NONE // La direction actuelle de Pac-Man
    // Initialise la classe PacMan

    init {
        val pacManRightOriginal = BitmapFactory.decodeResource(resources, R.drawable.kotlindroite)
        val pacManLeftOriginal = BitmapFactory.decodeResource(resources, R.drawable.kotlingauche)
        val pacManUpOriginal = BitmapFactory.decodeResource(resources, R.drawable.kotlinhaut)
        val pacManDownOriginal = BitmapFactory.decodeResource(resources, R.drawable.kotlinbas)

        pacManRightBitmap = Bitmap.createScaledBitmap(
            pacManRightOriginal,
            caseWidth.toInt(),
            caseHeight.toInt(),
            true
        )
        pacManLeftBitmap = Bitmap.createScaledBitmap(
            pacManLeftOriginal,
            caseWidth.toInt(),
            caseHeight.toInt(),
            true
        )
        pacManUpBitmap =
            Bitmap.createScaledBitmap(pacManUpOriginal, caseWidth.toInt(), caseHeight.toInt(), true)
        pacManDownBitmap = Bitmap.createScaledBitmap(
            pacManDownOriginal,
            caseWidth.toInt(),
            caseHeight.toInt(),
            true
        )

        pacManBitmap = pacManRightBitmap
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
        println("Before setPosition: tileX = $tileX, tileY = $tileY")
        tileX = newtileX
        tileY = newtileY
        println("After setPosition: tileX = $tileX, tileY = $tileY")
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
        pacManBitmap = pacManLeftBitmap
        nextTileX = tileX - speed
        if (!labyrinthe.isMur2(nextTileX, tileY) && !labyrinthe.isMur2(ceil(tileX - 1F), tileY)) {
            tileX = nextTileX
        }
    }

    fun moveRight() {
        pacManBitmap = pacManRightBitmap
        if (!labyrinthe.isMur2(tileX + 1F, tileY)) {
            nextTileX = tileX + speed
        }
        if (!labyrinthe.isMur2(nextTileX, tileY) && !labyrinthe.isMur2(tileX + 1F, tileY)) {
            tileX = nextTileX
        }
    }

    fun moveUp() {
        pacManBitmap = pacManUpBitmap
        nextTileY = tileY - speed

        if (!labyrinthe.isMur2(tileX, nextTileY) && !labyrinthe.isMur2(tileX, ceil(tileY - 1F))) {
            tileY = nextTileY
        }
    }

    fun moveDown() {
        pacManBitmap = pacManDownBitmap
        nextTileY = tileY + speed
        if (!labyrinthe.isMur2(tileX, nextTileY) && !labyrinthe.isMur2(
                tileX,
                tileY + 1F
            ) && !labyrinthe.isZoneInterdite(tileX, nextTileY) && !labyrinthe.isZoneInterdite(
                tileX,
                tileY + 1F
            )
        ) {
            tileY = nextTileY
        }
    }


    // Met à jour la position de Pac-Man en fonction de sa direction actuelle et de la carte du labyrinthe
    // et effectue des actions liées à la collision avec des points ou des fantômes
    // Suit le principe SRP, car elle contient des fonctions liées à la mise à jour de la position et de l'état de Pac-Man, qui sont des actions cohérentes
    // Elle suit également le principe OCP, car elle peut facilement être étendue pour des futures fonctionnalités liées à Pac-Man

    fun update(
        labyrinthe: Labyrinthe,
        score: Score,
        fantomes: List<MutableList<out Fantome>>,
        life: Life,
        bonus: List<Bonus>,
        gameView: GameView
    ) {

        when (direction) {
            Direction.NONE -> {
                // Ne bouge pas
            }
            Direction.UP -> {
                moveUp()
            }
            Direction.DOWN -> {
                moveDown()
            }
            Direction.LEFT -> {
                moveLeft()
            }
            Direction.RIGHT -> {
                moveRight()
            }
        }
        isOnTileX = abs(tileX - round(tileX)) == 0F
        isOnTileY = abs(tileY - round(tileY)) == 0F
        //println("voici tileX "+ tileX + "Voici tileY" + tileY)
        lastUpdateTime = System.currentTimeMillis()
        val pointsGained = eatPoint(labyrinthe)

        if (eatFantome) {
            for (fantomeList in fantomes) {
                for(fantome in fantomeList){
                fantome.isScary = true
            }}
        } else {
            for (fantomeList in fantomes) {
                for(fantome in fantomeList){
                fantome.isScary = false
                fantome.isVisible = true}
            }
        }

        score.incrementScore(pointsGained)
        teleport(labyrinthe)
        for (bonuss in bonus) {
            collectBonus(bonuss, life, gameView)
        }
        updateSpeed()
        updateEatFantome()
        // Vérifie s'il y a collision entre Pac-Man et les fantômes
        checkCollisionsWithGhosts(fantomes, life,score)
        checkIfStuck()
        println("nbe lignes = " + labyrinthe.nbLignes + "nbe Colonnes = " +labyrinthe.nbColonnes )
    }

    private fun checkIfStuck() {
        val currentTileX = round(tileX)
        val currentTileY = round(tileY)

        if (currentTileX == lastTileX && currentTileY == lastTileY) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - timeOnTile >= maxTimeOnTile) {
                setPosition(currentTileX, currentTileY)
                timeOnTile = currentTime
            }
        } else {
            lastTileX = currentTileX
            lastTileY = currentTileY
            timeOnTile = System.currentTimeMillis()
        }
    }


    fun collectBonus(bonus: Bonus, life: Life, gameView: GameView) {

        if (bonus.isVisible && tileX == bonus.tileX && tileY == bonus.tileY) {
            if (bonus is BonusCoeur) {
                life.increaseLife()
                bonus.hideBonus()
                bonus.isCollected = true
            }
        }
        if(bonus is BonusSwift){
            val bonusIterator = bonus.listBonusSwift.iterator() // permet de parcourir éléments listes
            while (bonusIterator.hasNext()) { // méthode hasNext() renvoie true s'il existe un élément suivant dans la liste, et false s'il n'y en a pas.
                val bonusswift = bonusIterator.next()
                if (bonus.isVisible && tileX == bonusswift.first && tileY == bonusswift.second){
                    speed = 1 / 32F
                    bonus.hideBonus()
                    bonus.isCollected = true
                    bonusIterator.remove() // Supprime le bonus de la liste après la collecte
                    lastBonusTime2 = System.currentTimeMillis()
                }
            }
        }

        else if(bonus is BonusCafe){
            val bonusIterator = bonus.listBonusCafe.iterator() // permet de parcourir éléments listes
            while (bonusIterator.hasNext()) { // méthode hasNext() renvoie true s'il existe un élément suivant dans la liste, et false s'il n'y en a pas.
                val bonusswift = bonusIterator.next()
                if (bonus.isVisible && tileX == bonusswift .first && tileY == bonusswift.second){
                    speed = 1 / 8F
                    bonus.hideBonus()
                    bonus.isCollected = true
                    bonusIterator.remove() // Supprime le bonus de la liste après la collecte
                    lastBonusTime = System.currentTimeMillis()
                }
            }

        }
    }

    fun updateSpeed() {
        val currentTime = System.currentTimeMillis()
        // Bonus actif pendant 5sec
        if (lastBonusTime > 0 && currentTime - lastBonusTime >= 5000) {
            // Réinitialiser la vitesse de Pac-Man à sa valeur normale
            speed = 1 / 16F
            lastBonusTime = 0
        }
        if (lastBonusTime2 > 0 && currentTime - lastBonusTime2 >= 5000) {
            // Réinitialiser la vitesse de Pac-Man à sa valeur normale
            speed = 1 / 16F
            lastBonusTime = 0
        }

    }

    fun updateEatFantome() {
        val currentTime = System.currentTimeMillis()
        // Quand Pac-Man a mangé un gros point, il a 5 secondes pour manger les fantômes effrayés
        if (eatFantome && currentTime - lastPointGrosTime >= 5000) {
            // Réinitialiser l'état de Pac-Man
            eatFantome = false
            lastPointGrosTime = 0
        }
    }

    // Téléporte Pac-Man s'il se trouve sur une case de téléportation dans la carte du labyrinthe
// Suit le principe SRP, car elle contient une fonction cohérente qui est liée à la téléportation de Pac-Man
    fun teleport(labyrinthe: Labyrinthe) {
        if (labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 6) { //mode facile
            if (labyrinthe.nbColonnes<30){ setPosition(22F, 12F)} // mode normal
            else if(labyrinthe.nbColonnes<50){setPosition(41F,12F)} // mode difficile
            else{setPosition(64F,12F)}
        } else if (labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 9) {
            setPosition(3F, 12F)
        }
    }


    // Vérifie s'il y a collision entre Pac-Man et les fantômes
// Suit le principe SRP, car elle contient une fonction cohérente qui est liée à la vérification des collisions entre Pac-Man et les fantômes
    fun checkCollisionsWithGhosts(fantomes: List<MutableList<out Fantome>>, life: Life, score: Score) {
        val currentTime = System.currentTimeMillis()

        // Vérifier si Pac-Man touche n'importe quel fantôme dans la liste
        for (fantomeList in fantomes) {
            for (fantome in fantomeList) {

                if (tileX.toInt() == fantome.tileX.toInt() && tileY.toInt() == fantome.tileY.toInt() && fantome.isVisible
                ) {
                    // Si Pac-Man rencontre un fantôme normal
                    if (!fantome.isScary) {
                        // Vérifier si suffisamment de temps s'est écoulé depuis la dernière collision
                        // Je laisse un délai de 1 seconde avant de toucher un autre fantôme
                        if (currentTime - lastCollisionTime >= 1000) {
                            life.decreaseLife()
                            lastCollisionTime = currentTime
                        }
                    }
                    // Si Pac-Man rencontre un fantôme effrayé
                    else {
                        if (eatFantome) {
                            if (currentTime - lastCollisionTime >= 1000) {
                                score.incrementScore(50)
                                fantome.isVisible = false
                                lastCollisionTime = currentTime
                            }
                        }
                    }
                }
            }
        }
    }


    // Dessine Pac-Man sur le canvas
    // Suit le principe SRP, car elle contient une fonction cohérente qui est liée au dessin de Pac-Man
    fun draw(canvas: Canvas) {
        canvas.drawBitmap(pacManBitmap, tileX* caseWidth, tileY * caseHeight, null)
    }


    // Mange les points présents sur la case actuelle de Pac-Man s'il y en a
    // Suit le principe SRP, car elle contient une fonction cohérente qui est liée à la consommation des points par Pac-Man
    fun eatPoint(labyrinthe: Labyrinthe): Int {
        val currentTime = System.currentTimeMillis()

        if (labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 2) {

            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 0)
            return 10
        }
        else if( labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 4){

            lastPointGrosTime = System.currentTimeMillis()
            eatFantome = true
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 0)
            return 10
        }
        else if(labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 7){
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 11)
            return 10
        }

        else if(labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 12){
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 21)
            return 10
        }

        else if(labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 13){
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 31)
            return 10
        }

        else if(labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 16){
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 61)
            return 10
        }

        else if(labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 17){
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 71)
            return 10
        }

        else if(labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 18){
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 81)
            return 10
        }

        return 0
    }



}


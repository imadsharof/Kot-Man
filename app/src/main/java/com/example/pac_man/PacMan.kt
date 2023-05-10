package com.example.pac_man

import android.content.res.Resources
import android.graphics.*
import kotlin.math.*


// Classe PacMan qui représente le personnage Pac-Man

// Elle suit les principes SRP et SR, car elle ne s'occupe que des actions liées à Pac-Man et ne contient que des fonctions qui sont directement liées à Pac-Man
// Elle suit également le principe OCP, car elle est facilement extensible pour de futures fonctionnalités liées à Pac-Man
class PacMan(
    resources: Resources, // Les ressources de l'application
    private val caseWidth: Float, // La largeur d'une case dans le labyrinthe
    private val caseHeight: Float, // La hauteur d'une case dans le labyrinthe
    private val labyrinthe: Labyrinthe,
    private val score: Score,
    private val fantomes: List<MutableList<out Fantome>>,
    private val life: Life,
    private val bonus: List<Bonus>

) : Movable, Observable {
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

    private var lastTileX = -1F
    private var lastTileY = -1F

    private var timeOnTile = 0L
    private val maxTimeOnTile = 1000 // 3 secondes en millisecondes


    var speed = 1 / 16F
    var eatFantome = false //si PacMan a mangé le fantome


    var lastUpdateTime: Long = 0

    var lastCollisionTime: Long = 0
    var lastBonusTime: Long = 0
    var lastBonusTime2: Long = 0
    var lastPointGrosTime: Long = 0


    var direction: Direction = Direction.NONE // La direction actuelle de Pac-Man

    override val observers = arrayListOf<Observer>()
    override fun hasUpdated() {
        eatFantome = false
        }

    init {
        val pacManRightOriginal = BitmapFactory.decodeResource(resources, R.drawable.kotlindroite)
        val pacManLeftOriginal = BitmapFactory.decodeResource(resources, R.drawable.kotlingauche)
        val pacManUpOriginal = BitmapFactory.decodeResource(resources, R.drawable.kotlinhaut)
        val pacManDownOriginal = BitmapFactory.decodeResource(resources, R.drawable.kotlinbas)

        pacManRightBitmap = Bitmap.createScaledBitmap(pacManRightOriginal, caseWidth.toInt(), caseHeight.toInt(), true)
        pacManLeftBitmap = Bitmap.createScaledBitmap(pacManLeftOriginal, caseWidth.toInt(), caseHeight.toInt(), true)
        pacManUpBitmap = Bitmap.createScaledBitmap(pacManUpOriginal, caseWidth.toInt(), caseHeight.toInt(), true)
        pacManDownBitmap = Bitmap.createScaledBitmap(pacManDownOriginal, caseWidth.toInt(), caseHeight.toInt(), true)
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

    // fonction qui permet de changer la position de Pac-Man
    fun setPosition(newtileX: Float, newtileY: Float) {
        tileX = newtileX
        tileY = newtileY
    }

    // Initialise la position de Pac-Man dans le labyrinthe
    fun spawnPacMan() {
        tileX = 13F
        tileY = 17F
    }

    //fonction qui permet à Pac-Man de se déplacer vers la gauche
    override fun moveLeft() {
        pacManBitmap = pacManLeftBitmap
        nextTileX = tileX - speed
        if (!labyrinthe.isMur2(nextTileX, tileY) && !labyrinthe.isMur2(ceil(tileX - 1F), tileY)) {
            tileX = nextTileX
        }
    }

    //fonction qui permet à Pac-Man de se déplacer vers la droite
    override  fun moveRight() {
        pacManBitmap = pacManRightBitmap
        if (!labyrinthe.isMur2(tileX + 1F, tileY)) {
            nextTileX = tileX + speed
        }
        if (!labyrinthe.isMur2(nextTileX, tileY) && !labyrinthe.isMur2(tileX + 1F, tileY)) {
            tileX = nextTileX
        }
    }

    //fonction qui permet à Pac-Man de se déplacer vers le haut
    override  fun moveUp() {
        pacManBitmap = pacManUpBitmap
        nextTileY = tileY - speed

        if (!labyrinthe.isMur2(tileX, nextTileY) && !labyrinthe.isMur2(tileX, ceil(tileY - 1F))) {
            tileY = nextTileY
        }
    }

    // fonction qui permet à Pac-Man de se déplacer vers le bas
    override  fun moveDown() {
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

    // fonction qui met à jour la position de Pac-Man dans le labyrinthe et les différents éléments du jeu
    // tels que les points, les bonus et les fantômes
    fun update() {

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

        lastUpdateTime = System.currentTimeMillis()
        val pointsGained = eatPoint()
        score.incrementScore(pointsGained)


        for(fantomeList in fantomes) {for(fantome in fantomeList){fantome.update(eatFantome)}}

        teleport()
        for (bonuss in bonus) { collectBonus(bonuss) }
        updateSpeed()
        updateEatFantome()
        // Vérifie s'il y a collision entre Pac-Man et les fantômes
        checkCollisionsWithGhosts()
        checkIfStuck()
    }

    fun updateEatFantome() {
        val currentTime = System.currentTimeMillis()
        // Quand Pac-Man a mangé un gros point, il a 5 secondes pour manger les fantômes effrayés
        if (eatFantome && currentTime - lastPointGrosTime >= 5000) {
            // Réinitialiser l'état de Pac-Man
            hasUpdated() // notifier les observateurs du changement d'état
            lastPointGrosTime = 0
        }
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


    //fonction qui permet à Pac-Man de collecter des bonus tels que le café ou le cœur
    fun collectBonus(bonus: Bonus) {

        if(bonus is BonusSwift){
            val bonusIterator = bonus.listBonusSwift.iterator() // permet de parcourir éléments listes
            while (bonusIterator.hasNext()) { // méthode hasNext() renvoie true s'il existe un élément suivant dans la liste, et false s'il n'y en a pas.
                val bonusswift = bonusIterator.next()
                if (bonus.isVisible && tileX == bonusswift.first && tileY == bonusswift.second){
                    setPosition(bonusswift.first,bonusswift.second)
                    speed = 1 / 32F
                    bonus.hideBonus()
                    bonus.isCollected = true
                    bonusIterator.remove() // Supprime le bonus de la liste après la collecte
                    lastBonusTime2 = System.currentTimeMillis()
                }
            }
        }

        else if(bonus is BonusCafe){
            val bonusIterator2 = bonus.listBonusCafe.iterator() // permet de parcourir éléments listes
            while (bonusIterator2.hasNext()) { // méthode hasNext() renvoie true s'il existe un élément suivant dans la liste, et false s'il n'y en a pas.
                val bonusscafe = bonusIterator2.next()
                if (bonus.isVisible && tileX == bonusscafe.first && tileY == bonusscafe.second){
                    speed = 1 / 8F
                    bonus.hideBonus()
                    bonus.isCollected = true
                    bonusIterator2.remove() // Supprime le bonus de la liste après la collecte
                    lastBonusTime = System.currentTimeMillis()
                }
            }

        }

        else if(bonus is BonusCoeur){
            val bonusIterator3 = bonus.listBonusCoeur.iterator() // permet de parcourir éléments listes
            while (bonusIterator3.hasNext()) { // méthode hasNext() renvoie true s'il existe un élément suivant dans la liste, et false s'il n'y en a pas.
                val bonusscoeur = bonusIterator3.next()
                if (bonus.isVisible && tileX == bonusscoeur.first && tileY == bonusscoeur.second){
                    life.increaseLife()
                    bonus.hideBonus()
                    bonus.isCollected = true
                    bonusIterator3.remove() // Supprime le bonus de la liste après la collecte
                    lastBonusTime = System.currentTimeMillis()
                }
            }

        }
    }

    //  fonction qui met à jour la vitesse de Pac-Man en fonction des bonus collectés
    fun updateSpeed() {
        val currentTime = System.currentTimeMillis()
        // Bonus actif pendant 5sec
        if (lastBonusTime > 0 && currentTime - lastBonusTime >= 5000) {
            // Réinitialiser la vitesse de Pac-Man à sa valeur normale
            setPosition(round(tileX),round(tileY))
            speed = 1 / 16F
            checkIfStuck()
            lastBonusTime = 0
        }
        if (lastBonusTime2 > 0 && currentTime - lastBonusTime2 >= 5000) {
            // Réinitialiser la vitesse de Pac-Man à sa valeur normale
            setPosition(round(tileX),round(tileY))
            speed = 1 / 16F
            checkIfStuck()
            lastBonusTime2 = 0
        }

    }

//  fonction qui permet à Pac-Man de se téléporter d'un point à un autre du labyrinthe
    fun teleport() {
        if (labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 6) { //mode facile
            if (labyrinthe.nbColonnes<30){ setPosition(21F, 12F)} // mode normal
            else if(labyrinthe.nbColonnes<50){setPosition(40F,12F)} // mode difficile
            else{setPosition(63F,12F)}
        } else if (labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 9) {
            setPosition(3F, 12F)
        }
    }

    // fonction qui permet de détecter les collisions entre Pac-Man et les fantômes
    fun checkCollisionsWithGhosts() {

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
                            life.decreaseLife(score)
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

    // fonction qui dessine Pac-Man dans le labyrinthe
    fun draw(canvas: Canvas) {
        canvas.drawBitmap(pacManBitmap, tileX* caseWidth, tileY * caseHeight, null)
    }

    //  fonction qui permet à Pac-Man de manger des points dans le labyrinthe.
    fun eatPoint(): Int {

        if (labyrinthe.getmapvalue(round(tileX), round(tileY), labyrinthe.map) == 2 && (tileX %1 ==0f && tileY%1 == 0f)) {
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 0)
            return 10
        }
        else if( labyrinthe.getmapvalue(round(tileX), round(tileY), labyrinthe.map) == 4 && (tileX %1 ==0f && tileY%1 == 0f)){

            lastPointGrosTime = System.currentTimeMillis()
            eatFantome = true
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 0)
            return 10
        }
        else if(labyrinthe.getmapvalue(round(tileX), round(tileY), labyrinthe.map) == 7 && (tileX %1 ==0f && tileY%1 == 0f)){
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 11)
            return 10
        }

        else if(labyrinthe.getmapvalue(round(tileX), round(tileY), labyrinthe.map) == 12 && (tileX %1 ==0f && tileY%1 == 0f)){
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 21)
            return 10
        }

        else if(labyrinthe.getmapvalue(round(tileX), round(tileY), labyrinthe.map) == 13 && (tileX %1 ==0f && tileY%1 == 0f)){
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 31)
            return 10
        }

        else if(labyrinthe.getmapvalue(round(tileX), round(tileY), labyrinthe.map) == 16 && (tileX %1 ==0f && tileY%1 == 0f)){
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 61)
            return 10
        }

        else if(labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 17 && (tileX %1 ==0f && tileY%1 == 0f)){
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 71)
            return 10
        }

        else if(labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 18 && (tileX %1 ==0f && tileY%1 == 0f)) {
            labyrinthe.setmapvalue(tileX, tileY, labyrinthe.map, 81)
            return 10
        }

        return 0
    }



}


package com.example.pac_man

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import kotlin.random.Random
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.round


/* définit une classe abstraite "Fantome" qui représente les fantômes
 dans le jeu Pac-Man. Les fantômes sont des objets mobiles qui peuvent se déplacer
 dans un labyrinthe en suivant des règles de mouvement spécifiques.*/
abstract class Fantome(
    private val resources: Resources,
    val caseWidth: Float,
    val caseHeight: Float,
    fantomeDrawable: Int,
    scaryFantomeDrawable: Int,
    private val labyrinthe: Labyrinthe
) : Movable,Observer {

    val fantomeBitmap: Bitmap
    val scaryFantomeBitmap: Bitmap


    var tileX: Float = 0F
    var tileY: Float = 0F

    var nextTileX = 0F
    var nextTileY = 0F

    var isExit = false

    var isVisible = true
    var isScary = false

    val speed = 1/16F // La vitesse de déplacement des fantômes

    var direction: PacMan.Direction = PacMan.Direction.UP

    val possibleDirections = arrayListOf(
        PacMan.Direction.LEFT, // 0
        PacMan.Direction.RIGHT, //1
        PacMan.Direction.UP,    //2
        PacMan.Direction.DOWN   //3
    )


    init {
        val fantomeOriginal = BitmapFactory.decodeResource(resources, fantomeDrawable) // Ajoutez votre image de fantôme ici
        fantomeBitmap = Bitmap.createScaledBitmap(fantomeOriginal, caseWidth.toInt(), caseHeight.toInt(), true)

        val scaryFantomeOriginal = BitmapFactory.decodeResource(resources, scaryFantomeDrawable)
        scaryFantomeBitmap = Bitmap.createScaledBitmap(scaryFantomeOriginal, caseWidth.toInt(), caseHeight.toInt(), true)
    }

    // fonction pour mettre à jour l'état du fantôme
    override fun update(eatFantome: Boolean) {
        if (eatFantome) {
            isScary = true
        } else {
            isScary = false
            isVisible = true
        }
    }

    //  déplacer le fantôme en bas
    override fun moveDown() {
        nextTileY = tileY +speed
        if (!labyrinthe.isMur2(tileX, nextTileY) && !labyrinthe.isMur2(tileX ,tileY + 1F)) {
            tileY = nextTileY
        }
    }

    //  déplacer le fantôme à gauche
    override fun moveLeft() {
        nextTileX = tileX - speed
        if (!labyrinthe.isMur2(nextTileX, tileY) && !labyrinthe.isMur2(ceil(tileX - 1F),tileY)) {
            tileX = nextTileX
        }
    }

    //  déplacer le fantôme à droite
    override fun moveRight() {
        if(!labyrinthe.isMur2(tileX + 1F,tileY)){
            nextTileX = tileX + speed}
        if (!labyrinthe.isMur2(nextTileX, tileY) && !labyrinthe.isMur2(tileX + 1F,tileY)) {
            tileX = nextTileX
        }
    }

    // déplacer le fantôme en haut
    override fun moveUp() {
        nextTileY = tileY - speed
        if (!labyrinthe.isMur2(tileX, nextTileY)&& !labyrinthe.isMur2(tileX ,ceil(tileY - 1F)) ) {
            tileY = nextTileY
        }

    }

    open fun spawnFantome() {
        // Initialise la position du fantôme dans le labyrinthe
        tileX = 11F
        tileY = 11F
    }

    // définir la position du fantôme dans le labyrinthe à une position donnée.
    fun setPosition(newtileX : Float,newtileY: Float) {
        tileX = newtileX
        tileY = newtileY
    }

    // dessiner le fantôme sur un canvas.
    // Selon l'état du fantôme, soit l'image normale du fantôme soit l'image effrayante sera dessinée.
    open fun draw(canvas: Canvas) {
        if(isVisible){
        if(!isScary){ canvas.drawBitmap(fantomeBitmap, tileX * caseWidth, tileY * caseHeight, null)}
        else{ canvas.drawBitmap(scaryFantomeBitmap, tileX * caseWidth, tileY * caseHeight, null) }}
    }

    // fonction pour déplacer aléatoirement le fantôme
    fun moveRandomly() {
        if (isExit) {
            updatemove()
        } else {
            val murH = labyrinthe.isMur2(tileX, ceil(tileY - 1F))
            val murB = labyrinthe.isMur2(tileX, tileY + 1F)
            val murG = labyrinthe.isMur2(ceil(tileX - 1F), tileY)
            val murD = labyrinthe.isMur2(tileX + 1F, tileY)

            // Création d'une fonction imbriquée car sans ca j'avais que des conditions if qui s'enchaînaient
            fun updateDirectionIfBothMurs(mur1: Boolean, mur2: Boolean, newDirection: PacMan.Direction) {
                if (mur1 && mur2) {
                    direction = newDirection
                }
            }

            updateDirectionIfBothMurs(murH, murD, PacMan.Direction.LEFT)
            updateDirectionIfBothMurs(murH, murG, PacMan.Direction.DOWN)
            updateDirectionIfBothMurs(murB, murG, PacMan.Direction.RIGHT)
            updateDirectionIfBothMurs(murB, murD, PacMan.Direction.UP)

            // Si fantome touche le 15
            if (!isExit && labyrinthe.getmapvalue(tileX, tileY, labyrinthe.map) == 15) {
                direction = PacMan.Direction.UP
                isExit = true
            }
        }

        when (direction) {
            PacMan.Direction.UP -> {
                moveUp()
            }
            PacMan.Direction.DOWN -> {
                moveDown()
            }
            PacMan.Direction.LEFT -> {
                moveLeft()
            }
            PacMan.Direction.RIGHT -> {
                moveRight()
            }
            else -> { /* Ne rien faire */
            }
        }
    }

    // fonction pour mettre à jour la direction de déplacement du fantôme
    fun updatemove(){
        if (direction == PacMan.Direction.UP){
            if(labyrinthe.isMur2(tileX ,ceil(tileY - 1F)) && !labyrinthe.isMur2(ceil(tileX - 1F),tileY) && !labyrinthe.isMur2(tileX + 1F,tileY)){

                val randomNumber1 = (0..1).random()
                direction = possibleDirections[randomNumber1]
            }
            else if (labyrinthe.isMur2(tileX ,ceil(tileY - 1F)) && labyrinthe.isMur2(ceil(tileX - 1F),tileY)){
                direction = PacMan.Direction.RIGHT
            }
            else if (labyrinthe.isMur2(tileX ,ceil(tileY - 1F)) && labyrinthe.isMur2(tileX + 1F,tileY)){
                direction = PacMan.Direction.LEFT
            }

            // Si fantome touche une inter proba qu'il tourne a gauche ou droite
            val randomNumber5 = (0..1).random()
            if(randomNumber5 == 0 &&
               labyrinthe.isIntersectiondouze(tileX,tileY,labyrinthe.map)&&
                (tileX % 1 == 0f && tileY %1 == 0f)
                )
            {
                val randomNumber6 = (0..1).random()
                direction = possibleDirections[randomNumber6]
            }
        }

        else if (direction == PacMan.Direction.DOWN ) {
            if(labyrinthe.isMur2(tileX ,tileY + 1F) && !labyrinthe.isMur2(ceil(tileX - 1F),tileY) && !labyrinthe.isMur2(tileX + 1F,tileY)){
                val randomNumber2 = (0..1).random()
                direction = possibleDirections[randomNumber2]
            }
            else if (labyrinthe.isMur2(ceil(tileX - 1F),tileY) && labyrinthe.isMur2(tileX ,tileY + 1F)){
                direction = PacMan.Direction.RIGHT
            }
            else if (labyrinthe.isMur2(tileX + 1F,tileY) &&labyrinthe.isMur2(tileX ,tileY + 1F) ){
                direction = PacMan.Direction.LEFT
            }

            // Si fantome touche une inter proba qu'il tourne a gauche ou droite
            val randomNumber9 = (0..1).random()
            if(randomNumber9 == 0 &&
                labyrinthe.isIntersectiondouze(tileX,tileY,labyrinthe.map)&&
                (tileX % 1 == 0f && tileY %1 == 0f)
            )
            {
                val randomNumber10 = (0..1).random()
                direction = possibleDirections[randomNumber10]
            }
        }

        else if (direction == PacMan.Direction.RIGHT) {
            if(labyrinthe.isMur2(tileX + 1F,tileY) && !labyrinthe.isMur2(tileX ,tileY + 1F) && !labyrinthe.isMur2(tileX ,ceil(tileY - 1F))){
                val randomNumber3 = (2..3).random()
                direction = possibleDirections[randomNumber3]
            }
            else if (labyrinthe.isMur2(tileX + 1F,tileY) && labyrinthe.isMur2(tileX ,ceil(tileY - 1F))){
                direction = PacMan.Direction.DOWN
            }
            else if (labyrinthe.isMur2(tileX + 1F,tileY) && labyrinthe.isMur2(tileX ,tileY + 1F) ){
                direction = PacMan.Direction.UP
            }

            // Si intersection 1 chance sur 2 de vouloir aller en haut ou pas c'est lui qui choisit
            val randomNumber7 = (0..1).random()
            if(randomNumber7 == 0 &&
                labyrinthe.isIntersectiontreizeouquatorze(tileX,tileY,labyrinthe.map)&&
                (tileX % 1 == 0f && tileY %1 == 0f)
            )
            {
                direction = PacMan.Direction.UP
            }

            // Si intersection :  1 chance sur 2 d'aller vers le bas
            val randomNumber16 = (0..1).random()
            if(randomNumber16 == 0 &&
                labyrinthe.isIntersectionseize(tileX,tileY,labyrinthe.map)&&
                (tileX % 1 == 0f && tileY %1 == 0f)
            )
            {
                direction = PacMan.Direction.DOWN
            }

        }
        else if (direction == PacMan.Direction.LEFT) {

            if(labyrinthe.isMur2(ceil(tileX - 1F),tileY) && !labyrinthe.isMur2(tileX ,ceil(tileY - 1F)) && !labyrinthe.isMur2(tileX ,tileY + 1F) ){
                val randomNumber4 = (2..3).random()
                direction = possibleDirections[randomNumber4]
            }
            else if (labyrinthe.isMur2(ceil(tileX - 1F),tileY) && labyrinthe.isMur2(tileX ,tileY + 1F)){
                direction = PacMan.Direction.UP

            }
            else if (labyrinthe.isMur2(ceil(tileX - 1F),tileY) && labyrinthe.isMur2(tileX ,ceil(tileY - 1F))){
                direction = PacMan.Direction.DOWN
            }

            // Si intersection 1 chance sur 2 de vouloir aller en haut ou pas c'est lui qui choisit
            val randomNumber8 = (0..1).random()
            if(randomNumber8 == 0 &&
                labyrinthe.isIntersectiontreizeouquatorze(tileX,tileY,labyrinthe.map)&&
                (tileX % 1 == 0f && tileY %1 == 0f)
            )
            {
                direction = PacMan.Direction.UP
            }

            val randomNumber18 = (0..1).random()
            if(randomNumber18 == 0 &&
                labyrinthe.isIntersectionseize(tileX,tileY,labyrinthe.map)&&
                (tileX % 1 == 0f && tileY %1 == 0f)
            )
            {
                direction = PacMan.Direction.DOWN
            }

        }

        if(direction == PacMan.Direction.DOWN ||direction == PacMan.Direction.UP){

            // 1 chance sur 2 d'aller a droite
            val randomNumber19 = (0..1).random()
            if(randomNumber19 == 0 &&
                labyrinthe.isIntersectiondixsept(tileX,tileY,labyrinthe.map)&&
                (tileX % 1 == 0f && tileY %1 == 0f)
            )
            {
                direction = PacMan.Direction.RIGHT
            }

            // une chance sur 2 d'aller a gauche
            val randomNumber20 = (0..1).random()
            if(randomNumber20 == 0 &&
                labyrinthe.isIntersectiondixhuit(tileX,tileY,labyrinthe.map)&&
                (tileX % 1 == 0f && tileY %1 == 0f)
            )
            {
                direction = PacMan.Direction.LEFT
            }

        }
    }
}

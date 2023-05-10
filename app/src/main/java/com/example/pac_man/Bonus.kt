package com.example.pac_man

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas


// La classe abstraite Bonus représente un bonus générique pour le jeu
// SOLID: Cette classe suit le principe de Responsabilité Unique (SRP)
// en ayant pour seule responsabilité la gestion des bonus
abstract class Bonus(
    private val resources: Resources,
    val caseWidth: Float,
    val caseHeight: Float,
    bonusDrawable: Int,
    bonusFrequency : Int,
    val labyrinthe: Labyrinthe

) {
    val bonusBitmap: Bitmap


    var tileX: Float = 0F
    var tileY: Float = 0F

    var startTime = System.currentTimeMillis()

    var isVisible: Boolean = false
    var isCollected: Boolean = false

    var timeBonus = 60 * bonusFrequency


    init {
        val bonusOriginal = BitmapFactory.decodeResource(resources, bonusDrawable)
        bonusBitmap =
            Bitmap.createScaledBitmap(bonusOriginal, caseWidth.toInt(),  caseHeight.toInt(), true)
    }

    // Dessine le bonus sur le canvas s'il est visible et non collecté
    // SOLID: SRP respecté (affichage du bonus)
    open fun draw(canvas: Canvas) {
        if (isVisible && !isCollected) {
            canvas.drawBitmap(bonusBitmap, tileX *  caseWidth, tileY *  caseHeight, null)
        }
    }

    // Fait apparaître le bonus à une position spécifique
    // SOLID: SRP respecté (gestion de l'apparition du bonus)
    fun spawnBonus() {
        if (!isVisible && !isCollected) {
            tileX = 12F
            tileY = 10F
            isVisible = true
            isCollected = false
        }
    }

    // Cache le bonus
    // SOLID: SRP respecté (gestion de la visibilité du bonus)
    open fun hideBonus() {
        isVisible = false
    }

    // Met à jour l'état du bonus en fonction du temps écoulé
    // SOLID: SRP respecté (mise à jour de l'état du bonus)
    open fun update() {
        val elapsedTime = System.currentTimeMillis() - startTime
        val elapsedSeconds = elapsedTime / 1000

        // Le bonus apparait de 00:30 sec à 00:40 puis disparait et revient chaque apres timeBonus minute
        if (elapsedSeconds % timeBonus >= 30 && elapsedSeconds % timeBonus < 40) {
            if (!isCollected) {
                spawnBonus()
            }

            // Le bonus disparait de 00:40 à jusque (1:30 + timeBonus ) et revient apres chaque timeBonus minute
        } else if (elapsedSeconds % timeBonus >= 40 ) {
            isCollected = false
            isVisible = false

            // Avant les 30 sec le bonus n'apparait pas
        } else {
            hideBonus()
        }

    }
}
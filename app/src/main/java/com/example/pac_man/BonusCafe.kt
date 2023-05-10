package com.example.pac_man

import android.content.res.Resources
import android.graphics.Canvas

class BonusCafe(
    resources: Resources,
    caseWidth: Float,
    caseHeight: Float,
    labyrinthe: Labyrinthe
) : Bonus(resources, caseWidth,caseHeight, R.drawable.bonuscafe,1, labyrinthe) {

    val listBonusCafe = mutableListOf<Pair<Float, Float>>()

    // responsable du dessin de l'objet
    override fun draw(canvas: Canvas) {
        if (isVisible && !isCollected) {

            for (y in labyrinthe.map.indices) {
                for (x in labyrinthe.map[y].indices){
                    if(labyrinthe.map[y][x] == 89){
                        canvas.drawBitmap(bonusBitmap, x * caseWidth, y * caseHeight, null)
                        listBonusCafe.add(Pair(x.toFloat(), y.toFloat()))
                    }
                }
            }
        }
    }

    override fun update() {
        val elapsedTime = System.currentTimeMillis() - startTime
        val elapsedSeconds = elapsedTime / 1000

        // Le bonus apparait de 00:30 sec à 00:40 puis disparait et revient chaque apres timeBonus minute
        if (elapsedSeconds % timeBonus >= 40 && elapsedSeconds % timeBonus < 50) {
            if (!isCollected) {
                spawnBonus()
            }

            // Le bonus disparait de 00:40 à jusque (1:30 + timeBonus ) et revient apres chaque timeBonus minute
        } else if (elapsedSeconds % timeBonus >= 50 ) {
            isCollected = false
            isVisible = false

            // Avant les 30 sec le bonus n'apparait pas
        } else {
            hideBonus()
        }

    }
}


package com.example.pac_man

import android.content.res.Resources
import android.graphics.Canvas

class BonusSwift(
    resources: Resources,
    caseWidth: Float,
    caseHeight: Float
) : Bonus(resources, caseWidth, caseHeight, R.drawable.bonusswift,1) {

    val listBonusSwift = mutableListOf<Pair<Float, Float>>()


    override fun draw(canvas: Canvas,labyrinthe: Labyrinthe) {
        if (isVisible && !isCollected) {

            for (y in labyrinthe.map.indices) {
                for (x in labyrinthe.map[y].indices){
                    if(labyrinthe.map[y][x] == 12 || labyrinthe.map[y][x] == 13 || labyrinthe.map[y][x] == 31 || labyrinthe.map[y][x] == 21){
                        canvas.drawBitmap(bonusBitmap, x * caseWidth, y * caseHeight, null)
                        listBonusSwift.add(Pair(x.toFloat(), y.toFloat()))
                    }
                }
            }
        }
        }


    override fun update() {
        val elapsedTime = System.currentTimeMillis() - startTime
        val elapsedSeconds = elapsedTime / 1000

        // Le bonus apparait de 00:50 sec à 00:60 puis disparait et revient chaque apres timeBonus minute
        if (elapsedSeconds % timeBonus >= 15 && elapsedSeconds % timeBonus < 30) {
            if (!isCollected) {
                spawnBonus()
            }

            // Le bonus disparait de 00:40 à jusque (1:30 + timeBonus ) et revient apres chaque timeBonus minute
        } else if (elapsedSeconds % timeBonus >= 30 ) {
            isCollected = false
            isVisible = false

            // Avant les 30 sec le bonus n'apparait pas
        } else {
            hideBonus()
        }

    }
}

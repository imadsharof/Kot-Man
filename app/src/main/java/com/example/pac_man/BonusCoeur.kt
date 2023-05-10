package com.example.pac_man

import android.content.res.Resources
import android.graphics.Canvas

class BonusCoeur(
    resources: Resources,
    caseWidth: Float,
    caseHeight: Float,
    labyrinthe: Labyrinthe
) : Bonus(resources, caseWidth,caseHeight, R.drawable.bonuscoeur,1,labyrinthe) {

    val listBonusCoeur = mutableListOf<Pair<Float, Float>>()
    override fun draw(canvas: Canvas) {
        if (isVisible && !isCollected) {

            for (y in labyrinthe.map.indices) {
                for (x in labyrinthe.map[y].indices){
                    if(labyrinthe.map[y][x] ==45){
                        canvas.drawBitmap(bonusBitmap, x * caseWidth, y * caseHeight, null)
                        listBonusCoeur.add(Pair(x.toFloat(), y.toFloat()))
                    }
                }
            }
        }
    }
}
package com.example.pac_man

import android.content.res.Resources
import android.graphics.Canvas

class FantomeBleu(
    resources: Resources,
    caseWidth: Float,
    caseHeight: Float
) : Fantome(resources, caseWidth, caseHeight, R.drawable.fantomebleu,R.drawable.fantomegris) {

    override fun spawnFantome() {
        // Initialise la position du fantôme vert dans le labyrinthe
        tileX = 12F
        tileY = 13F
    }

    /*override fun draw(canvas: Canvas,labyrinthe: Labyrinthe) {
        if(isVisible){
            for (y in labyrinthe.map.indices) {
                for (x in labyrinthe.map[y].indices){
                    if(labyrinthe.map[y][x] == 40) {
                        if(!isScary){
                            canvas.drawBitmap(fantomeBitmap, x * caseWidth, y* caseHeight, null)
                            listCoordFantomeBleu.add(Pair(x.toFloat(), y.toFloat()))
                        }
                        else{
                            canvas.drawBitmap(scaryFantomeBitmap, x* caseWidth, y * caseHeight, null)
                            listCoordFantomeBleuScary.add(Pair(x.toFloat(), y.toFloat()))
                    }
                }
            }
        }
    }
}*/
    }
package com.example.pac_man

import android.content.res.Resources
import android.graphics.Canvas

class PointGros (private val resources: Resources, caseWidth: Float, caseHeight: Float,labyrinthe: Labyrinthe) : Point(caseWidth, caseHeight,labyrinthe) {


    init {
        paint.color = resources.getColor(R.color.pointColor) // Remplace 'R.color.dotColor' par la couleur de ton choix.
    }

    override fun draw(canvas: Canvas) {
        for (i in labyrinthe.map.indices) {
            for (j in labyrinthe.map[i].indices) {
                if (labyrinthe.map[i][j] == 4) { // Remplace '4' par la valeur représentant un poFloat dans ton tableau.
                    canvas.drawCircle(
                        (j * caseWidth + caseWidth / 2).toFloat(),
                        (i * caseHeight + caseHeight / 2).toFloat(),
                        (Math.min(caseWidth, caseHeight) * 0.40).toFloat(),
                        paint
                    )
                }
            }
        }
    }
}
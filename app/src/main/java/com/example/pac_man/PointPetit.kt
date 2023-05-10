package com.example.pac_man

import android.content.res.Resources
import android.graphics.Canvas


class PointPetit(private val resources: Resources, caseWidth: Float, caseHeight: Float,labyrinthe: Labyrinthe) : Point(caseWidth, caseHeight,labyrinthe) {

    init {
        paint.color = resources.getColor(R.color.pointColor) // Remplace 'R.color.dotColor' par la couleur de ton choix.
    }

    // dessiner des cercles sur le Canvas à des positions spécifiques
    // sur la matrice de la map labyrinthe en utilisant les dimensions de la case
    override fun draw(canvas: Canvas) {
        for (i in labyrinthe.map.indices) {
            for (j in labyrinthe.map[i].indices) {
                if (labyrinthe.map[i][j] == 2 ||
                    labyrinthe.map[i][j] == 7 ||
                    labyrinthe.map[i][j] == 12 ||
                    labyrinthe.map[i][j] == 13 ||
                    labyrinthe.map[i][j] == 16||
                    labyrinthe.map[i][j] == 17 ||
                    labyrinthe.map[i][j] == 18
                ) {
                    canvas.drawCircle(
                        (j * caseWidth + caseWidth / 2).toFloat(),
                        (i * caseHeight + caseHeight / 2).toFloat(),
                        (Math.min(caseWidth, caseHeight) * 0.15).toFloat(),
                        paint
                    )
                }
            }
        }
    }
}
package com.example.pac_man

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint


class PointGris(private val resources: Resources, caseWidth: Int, caseHeight: Int) : Point(resources, caseWidth, caseHeight) {

    init {
        paint.color = resources.getColor(R.color.pointColor) // Remplace 'R.color.dotColor' par la couleur de ton choix.
    }

    override fun draw(canvas: Canvas, map: Array<IntArray>) {
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == 2) { // Remplace '2' par la valeur représentant un point dans ton tableau.
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
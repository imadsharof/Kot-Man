package com.example.pac_man

import android.content.res.Resources
import android.graphics.Canvas


class PointPetit(private val resources: Resources, caseWidth: Float, caseHeight: Float) : Point(resources, caseWidth, caseHeight) {

    init {
        paint.color = resources.getColor(R.color.pointColor) // Remplace 'R.color.dotColor' par la couleur de ton choix.
    }

    override fun draw(canvas: Canvas, map: Array<IntArray>) {
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == 2 ||
                    map[i][j] == 7 ||
                    map[i][j] == 12 ||
                    map[i][j] == 13 ||
                    map[i][j] == 16||
                    map[i][j] == 17 ||
                    map[i][j] == 18
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
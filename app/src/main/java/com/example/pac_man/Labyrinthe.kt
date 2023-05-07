package com.example.pac_man

import android.content.res.Resources
import android.graphics.*

class Labyrinthe(private val resources: Resources, val caseWidth: Float, val caseHeight: Float) {


    private val wallBitmap: Bitmap

    init {
        val wallOriginal = BitmapFactory.decodeResource(resources, R.drawable.binaire)
        wallBitmap = Bitmap.createScaledBitmap(wallOriginal, caseWidth.toInt(),
            caseHeight.toInt(), true)
    }

    companion object {
        const val NB_COLONNES = 25
        const val NB_LIGNES = 27
    }

    val initialmap = arrayOf(
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 4, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 4, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 3, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 3, 3, 3, 3, 3, 3, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 3, 1, 1, 3, 1, 1, 3, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 2, 2, 2, 2, 2, 2, 2, 3, 1, 3, 3, 3, 1, 3, 2, 2, 2, 2, 2, 2, 2, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 3, 1, 3, 3, 3, 1, 3, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 1, 1, 1, 1, 1, 3, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 3, 3, 3, 3, 3, 3, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 4, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 4, 1, 0, 0),
        intArrayOf(0, 0, 1, 1, 2, 2, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 2, 2, 1, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    )

    // 27 ligne 25 colonne
    var map =  arrayOf(
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2,16, 2, 2, 2, 2, 1, 2, 2, 2, 2,16, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 4, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 4, 1, 0, 0),
        intArrayOf(0, 0, 1,17, 2, 2, 2,12, 2,16, 2,13, 2,13, 2,16, 2,12, 2, 2, 2,18, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2, 7, 1, 7, 2, 7, 1, 7, 2, 7, 1, 7, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 3, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 3,14, 3,14, 3, 8, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 3, 1, 1,99, 1, 1, 3, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 6, 2, 2, 2, 5, 7, 1, 3, 1, 3,15, 3, 1, 8, 2, 2 , 5, 2, 2, 2, 9, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 3, 1, 3, 3, 3, 1, 3, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 1, 1, 1, 1, 1, 3, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 8, 3, 3, 3, 3, 3, 8, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2,12, 2,13, 2, 2, 1, 2, 2,13, 2,12, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 4, 2, 1, 1, 2, 2,16, 2,13, 2,13, 2,16, 2, 2, 1, 1, 2, 4, 1, 0, 0),
        intArrayOf(0, 0, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 0, 0),
        intArrayOf(0, 0, 1, 2,13, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2,13 , 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2,13, 2,13, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    )

    val nbLignes = map.size
    val nbColonnes = map[0].size

    fun isMur(x: Float, y: Float): Boolean {
        if (x < 0 || x >= nbColonnes || y < 0 || y >= nbLignes) {
            return true // les bords sont considérés comme des murs
        }
        return map[y.toInt()][x.toInt()] == 1
    }

    fun isMur2(tileX: Float, tileY: Float): Boolean {
        if (tileX < 0 || tileX >= nbColonnes || tileY < 0 || tileY >= nbLignes) {
            return true // les bords sont considérés comme des murs
        }
        return  map[tileY.toInt()][tileX.toInt()] == 1
    }

    fun isZoneInterdite(tileX: Float, tileY: Float): Boolean {
        if (tileX < 0 || tileX >= nbColonnes || tileY < 0 || tileY >= nbLignes) {
            return true // les bords sont considérés comme des murs
        }
        return  map[tileY.toInt()][tileX.toInt()] == 99
    }
    fun isIntersectiondouze(tileX: Float, tileY: Float, map: Array<IntArray>) : Boolean {
        return (map[tileY.toInt()][tileX.toInt()] == 12||
                map[tileY.toInt()][tileX.toInt()] == 21)
    }

    fun isIntersectiontreizeouquatorze(tileX: Float, tileY: Float, map: Array<IntArray>) : Boolean {
        return (map[tileY.toInt()][tileX.toInt()] == 13 ||
                map[tileY.toInt()][tileX.toInt()] == 14 ||
                map[tileY.toInt()][tileX.toInt()] == 12 ||
                map[tileY.toInt()][tileX.toInt()] == 21 ||
                map[tileY.toInt()][tileX.toInt()] == 61
                )
    }

    fun isIntersectionseize(tileX: Float, tileY: Float, map: Array<IntArray>) : Boolean {
        return (map[tileY.toInt()][tileX.toInt()] == 16 ||
                map[tileY.toInt()][tileX.toInt()] == 12 ||
                map[tileY.toInt()][tileX.toInt()] == 21 ||
                map[tileY.toInt()][tileX.toInt()] == 61 )
    }

    fun isIntersectiondixsept(tileX: Float, tileY: Float, map: Array<IntArray>) : Boolean {
        return (map[tileY.toInt()][tileX.toInt()] == 17 ||
                map[tileY.toInt()][tileX.toInt()] == 71)
    }

    fun isIntersectiondixhuit(tileX: Float, tileY: Float, map: Array<IntArray>) : Boolean {
        return (map[tileY.toInt()][tileX.toInt()] == 18 ||
                map[tileY.toInt()][tileX.toInt()] == 81)
    }

    fun getmapvalue(tileX: Float, tileY: Float, map: Array<IntArray>): Int {
        return map[tileY.toInt()][tileX.toInt()]
    }

    fun setmapvalue(tileX: Float, tileY: Float, map: Array<IntArray>, value: Int) {
        map[tileY.toInt()][tileX.toInt()] = value
    }



    // Fun draw dessine avec des briques
    /*fun draw(canvas: Canvas) {
        for (i in 0 until nbLignes) {
            for (j in 0 until nbColonnes) {
                val left = j * caseWidth
                val top = i * caseHeight

                if (isMur(j, i)) {
                    canvas.drawBitmap(wallBitmap, left.toFloat(), top.toFloat(), null)
                }
            }
        }
    }*/

    fun draw(canvas: Canvas) {
        val borderWidth = 5f // ajustez la valeur de l'épaisseur de bordure
        val paint = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.FILL
        }
        val borderPaint = Paint().apply {
            color = Color.BLUE
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = borderWidth
        }


        for (i in 0 until nbLignes) {
            for (j in 0 until nbColonnes) {
                val left = j * caseWidth
                val top = i * caseHeight

                if (isMur(j.toFloat(), i.toFloat())) {
                    // Dessiner le rectangle intérieur en noir
                    canvas.drawRect(left + borderWidth, top + borderWidth, left + caseWidth - borderWidth, top + caseHeight - borderWidth, paint)

                    // Dessiner les bords adjacents en bleu
                    if (i == 0 || !isMur(j.toFloat(), (i - 1).toFloat())) {
                        // Dessiner le bord supérieur
                        canvas.drawRect(left.toFloat() , top.toFloat(), (left + caseWidth).toFloat() , top.toFloat(), borderPaint)
                    }
                    if (j == 0 || !isMur((j - 1).toFloat(), i.toFloat())) {
                        // Dessiner le bord gauche
                        canvas.drawRect(left.toFloat(), top.toFloat(), left.toFloat(), (top + caseHeight).toFloat() , borderPaint)
                    }
                    if (i == nbLignes - 1 || !isMur(j.toFloat(), (i + 1).toFloat())) {
                        // Dessiner le bord inférieur
                        canvas.drawRect(left.toFloat(), (top + caseHeight).toFloat() , (left + caseWidth).toFloat() , (top + caseHeight).toFloat(), borderPaint)
                    }
                    if (j == nbColonnes - 1 || !isMur((j + 1).toFloat(), i.toFloat())) {
                        // Dessiner le bord droit
                        canvas.drawRect((left + caseWidth).toFloat() , top.toFloat(), (left + caseWidth).toFloat(), (top + caseHeight).toFloat(), borderPaint)
                    }
                }
            }
        }
    }



}

package com.example.pac_man

import android.content.res.Resources
import android.graphics.*
import kotlin.math.ceil

// ce code respecte le principe de responsabilité unique (SRP) en définissant une seule classe pour la gestion et le dessin du labyrinthe.
class Labyrinthe(private val resources: Resources, val caseWidth: Float, val caseHeight: Float, val mode : String) {



    var wallOriginal: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.brique)
    var wallBitmap: Bitmap = Bitmap.createScaledBitmap(wallOriginal, caseWidth.toInt(),caseHeight.toInt(), true)

    // 27 ligne 25 colonne
    var map1 =  arrayOf(
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
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 3,14,45,14, 3, 8, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 3, 1, 1,99, 1, 1, 3, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 6, 2, 2, 2, 5, 7, 1, 3, 1, 3,15,42, 1, 8, 2, 2, 5, 2, 2, 2, 9, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 3, 1,40, 3,41, 1, 3, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 1, 1, 1, 1, 1, 3, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 8, 3, 3,89, 3, 3, 8, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2,12, 2,13, 2, 2, 1, 2, 2,13, 2,12, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 4, 2, 1, 1, 2, 2,16, 2,13, 2,13, 2,16, 2, 2, 1, 1, 2, 4, 1, 0, 0),
        intArrayOf(0, 0, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 0, 0),
        intArrayOf(0, 0, 1, 2,13, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2,13, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2,13, 2,13, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    )

    var map2 =  arrayOf(
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2,16, 2, 2, 2, 2, 1, 2, 2, 2, 2,16, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2,16, 2, 2, 2, 2, 1, 2, 2, 2, 2,16, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 4, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 4, 1, 0, 0),
        intArrayOf(0, 0, 1,17, 2, 2, 2,12, 2,16, 2,13, 2,13, 2,16, 2,12, 2, 2, 2,18, 1, 1,17, 2, 2, 2,12, 2,16, 2,13, 2,13, 2,16, 2,12, 2, 2, 2,18, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2, 7, 1, 7, 2, 7, 1, 7, 2, 7, 1, 7, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 7, 1, 7, 2, 7, 1, 7, 2, 7, 1, 7, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 3, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 3,14,45,14, 3, 8, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 3,14,45,14, 3, 8, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 3, 1, 1,99, 1, 1, 3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 1, 1,99, 1, 1, 3, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 6, 2, 2, 2, 5, 7, 1, 3, 1, 3,15,42, 1, 8, 2, 2, 5, 2, 2, 2, 2, 2, 2, 2, 2, 5, 7, 1, 3, 1, 3,15,42, 1, 8, 2, 2, 5, 2, 2, 2, 9, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 3, 1,40, 3,41, 1, 3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 1,40, 3,41, 1, 3, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 1, 1, 1, 1, 1, 3, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 1, 1, 1, 1, 1, 3, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 8, 3, 3,89, 3, 3, 8, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 8, 3, 3,89, 3, 3, 8, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2,12, 2,13, 2, 2, 1, 2, 2,13, 2,12, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2,12, 2,13, 2, 2, 1, 2, 2,13, 2,12, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 4, 2, 1, 1, 2, 2,16, 2,13, 2,13, 2,16, 2, 2, 1, 1, 2, 4, 1, 1, 4, 2, 1, 1, 2, 2,16, 2,13, 2,13, 2,16, 2, 2, 1, 1, 2, 4, 1, 0, 0),
        intArrayOf(0, 0, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 0, 0),
        intArrayOf(0, 0, 1, 2,13, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2,13, 2, 1, 1, 2,13, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2,13, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2,13, 2,13, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2,13, 2,13, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    )


    var map3 =  arrayOf(
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2,16, 2, 2, 2, 2, 1, 2, 2, 2, 2,16, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2,16, 2, 2, 2, 2, 1, 2, 2, 2, 2,16, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2,16, 2, 2, 2, 2, 1, 2, 2, 2, 2,16, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 4, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 4, 1, 0, 0),
        intArrayOf(0, 0, 1,17, 2, 2, 2,12, 2,16, 2,13, 2,13, 2,16, 2,12, 2, 2, 2,18, 1, 1,17, 2, 2, 2,12, 2,16, 2,13, 2,13, 2,16, 2,12, 2, 2, 2,18, 1, 1,17, 2, 2, 2,12, 2,16, 2,13, 2,13, 2,16, 2,12, 2, 2, 2,18, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2, 7, 1, 7, 2, 7, 1, 7, 2, 7, 1, 7, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 7, 1, 7, 2, 7, 1, 7, 2, 7, 1, 7, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 7, 1, 7, 2, 7, 1, 7, 2, 7, 1, 7, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 3, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 3,14,45,14, 3, 8, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 3,14,45,14, 3, 8, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 3,14,45,14, 3, 8, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 3, 1, 1,99, 1, 1, 3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 1, 1,99, 1, 1, 3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 1, 1,99, 1, 1, 3, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 6, 2, 2, 2, 5, 7, 1, 3, 1, 3,15,42, 1, 8, 2, 2, 5, 2, 2, 2, 2, 2, 2, 2, 2, 5, 7, 1, 3, 1, 3,15,42, 1, 8, 2, 2, 5, 2, 2, 2, 2, 2, 2, 2, 2, 5, 7, 1, 3, 1, 3,15,42, 1, 8, 2, 2, 5, 2, 2, 2, 9, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 3, 1,40, 3,41, 1, 3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 1,40, 3,41, 1, 3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 1,40, 3,41, 1, 3, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 1, 1, 1, 1, 1, 3, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 1, 1, 1, 1, 1, 3, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 3, 1, 1, 1, 1, 1, 3, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 2, 1, 8, 3, 3,89, 3, 3, 8, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 8, 3, 3,89, 3, 3, 8, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 8, 3, 3,89, 3, 3, 8, 1, 2, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2,12, 2,13, 2, 2, 1, 2, 2,13, 2,12, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2,12, 2,13, 2, 2, 1, 2, 2,13, 2,12, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2,12, 2,13, 2, 2, 1, 2, 2,13, 2,12, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 4, 2, 1, 1, 2, 2,16, 2,13, 2,13, 2,16, 2, 2, 1, 1, 2, 4, 1, 1, 4, 2, 1, 1, 2, 2,16, 2,13, 2,13, 2,16, 2, 2, 1, 1, 2, 4, 1, 1, 4, 2, 1, 1, 2, 2,16, 2,13, 2,13, 2,16, 2, 2, 1, 1, 2, 4, 1, 0, 0),
        intArrayOf(0, 0, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 0, 0),
        intArrayOf(0, 0, 1, 2,13, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2,13, 2, 1, 1, 2,13, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2,13, 2, 1, 1, 2,13, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2,13, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2,13, 2,13, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2,13, 2,13, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2,13, 2,13, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    )

        val map : Array<IntArray> =
        if (mode == "arcade") {
            map1
        } else if (mode == "facile") {
            map1
        } else if (mode == "normal") {
            map2
        } else if (mode == "difficile") {
            map3
        } else {
            map1
        }

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
        return map[ceil(tileY).toInt()][ceil(tileX).toInt()]
    }

    fun setmapvalue(tileX: Float, tileY: Float, map: Array<IntArray>, value: Int) {
        map[tileY.toInt()][tileX.toInt()] = value
    }



    fun draw(canvas: Canvas) {
        // Ne dessine pas avec des images les murs si le mode de jeu est en arcade
        if(mode == "arcade") {
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
                        canvas.drawRect(
                            left + borderWidth,
                            top + borderWidth,
                            left + caseWidth - borderWidth,
                            top + caseHeight - borderWidth,
                            paint
                        )

                        // Dessiner les bords adjacents en bleu
                        if (i == 0 || !isMur(j.toFloat(), (i - 1).toFloat())) {
                            // Dessiner le bord supérieur
                            canvas.drawRect(
                                left.toFloat(),
                                top.toFloat(),
                                (left + caseWidth).toFloat(),
                                top.toFloat(),
                                borderPaint
                            )
                        }
                        if (j == 0 || !isMur((j - 1).toFloat(), i.toFloat())) {
                            // Dessiner le bord gauche
                            canvas.drawRect(
                                left.toFloat(),
                                top.toFloat(),
                                left.toFloat(),
                                (top + caseHeight).toFloat(),
                                borderPaint
                            )
                        }
                        if (i == nbLignes - 1 || !isMur(j.toFloat(), (i + 1).toFloat())) {
                            // Dessiner le bord inférieur
                            canvas.drawRect(
                                left.toFloat(),
                                (top + caseHeight).toFloat(),
                                (left + caseWidth).toFloat(),
                                (top + caseHeight).toFloat(),
                                borderPaint
                            )
                        }
                        if (j == nbColonnes - 1 || !isMur((j + 1).toFloat(), i.toFloat())) {
                            // Dessiner le bord droit
                            canvas.drawRect(
                                (left + caseWidth).toFloat(),
                                top.toFloat(),
                                (left + caseWidth).toFloat(),
                                (top + caseHeight).toFloat(),
                                borderPaint
                            )
                        }
                    }
                }
            }

        }
        else{
            if(mode == "difficile"){wallOriginal = BitmapFactory.decodeResource(resources, R.drawable.pacman_droite_ouvre_bouche)
                wallBitmap = Bitmap.createScaledBitmap(wallOriginal, caseWidth.toInt(),caseHeight.toInt(), true)
            }
            if(mode == "facile"){wallOriginal = BitmapFactory.decodeResource(resources, R.drawable.binaire)
                wallBitmap = Bitmap.createScaledBitmap(wallOriginal, caseWidth.toInt(),caseHeight.toInt(), true)
            }

            for (i in 0 until nbLignes) {
                for (j in 0 until nbColonnes) {
                    val left = j * caseWidth
                    val top = i * caseHeight

                    if (isMur(j.toFloat(), i.toFloat())) {
                        canvas.drawBitmap(wallBitmap, left.toFloat(), top.toFloat(), null)
                    }
                }
            }
        }
    }

}

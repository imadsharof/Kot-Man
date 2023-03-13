package com.example.pac_man

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder

class Mur(private val surfaceView: SurfaceView) {
    private var holder: SurfaceHolder = surfaceView.holder

    fun dessiner() {
        val canvas = holder.lockCanvas()


        // Dessiner le fond noir
        val paintFond = Paint()
        paintFond.color = Color.BLACK
        canvas.drawPaint(paintFond)



        // Dessiner les murs intérieurs
        val paintInterieur = Paint()
        paintInterieur.color = Color.BLUE
        paintInterieur.strokeWidth = 10f

        //Dessin haut map
        canvas.drawLine(100f, 100f, surfaceView.width - 100f, 100f, paintInterieur)

        // Dessin mur droit
        canvas.drawLine(surfaceView.width - 100f, 100f, surfaceView.width - 100f, surfaceView.height/2 - 50f, paintInterieur)
        canvas.drawLine(surfaceView.width - 100f, surfaceView.height/2 + 50f, surfaceView.width - 100f, surfaceView.height - 100f, paintInterieur)

        // Dessin mur bas
        canvas.drawLine(surfaceView.width - 100f, surfaceView.height - 100f, 100f, surfaceView.height - 100f, paintInterieur)

        // Dessin mur gauche
        canvas.drawLine(100f, surfaceView.height - 100f, 100f, surfaceView.height/2 + 50f, paintInterieur)
        canvas.drawLine(100f, surfaceView.height/2 - 50f, 100f, 100f, paintInterieur)

        // Dessin mur pour que pacman passe de droite a gauche
        canvas.drawLine(surfaceView.width - 100f,surfaceView.height/2 - 50f,surfaceView.width + 0f ,surfaceView.height/2 - 50f,paintInterieur)
        canvas.drawLine(surfaceView.width - 100f,surfaceView.height/2 + 50f,surfaceView.width + 0f ,surfaceView.height/2 + 50f,paintInterieur)

        // Dessin mur pour que pacman passe de gauche à droite
        canvas.drawLine(100f,surfaceView.height/2 + 50f, 0f ,surfaceView.height/2 + 50f,paintInterieur)
        canvas.drawLine( 100f,surfaceView.height/2 - 50f, 0f ,surfaceView.height/2 - 50f,paintInterieur)



        // Déverrouiller le canvas pour terminer le dessin
        holder.unlockCanvasAndPost(canvas)
    }
}
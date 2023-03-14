package com.example.pac_man

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder

class Mur(private val surfaceView: SurfaceView) {
    private var holder: SurfaceHolder = surfaceView.holder
    private val w: Float = surfaceView.width.toFloat()
    private val h: Float = surfaceView.height.toFloat()

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

        val paintInterieur2 = Paint()
        paintInterieur2.color = Color.WHITE
        paintInterieur2.strokeWidth = 10f

        //Dessin haut map
        canvas.drawLine(100f, 100f, w - 100f, 100f, paintInterieur)

        /* Dessin mur droit*/

            // haut
        canvas.drawLine(w-100f,100f,w-100f,(h-200f)*1/3 + 100f,paintInterieur)

            // bas
        canvas.drawLine(w-100f,(h-200f)*2/3 + 100f , w-100f,h-100f,paintInterieur)

            //sortie pacman haut
        canvas.drawLine(w-100f,(h-200f)*1/3 + 100f,(w-200f)*9/10 + 100f,(h-200f)*1/3 + 100f,paintInterieur)
        canvas.drawLine((w-200f)*9/10 + 100f,(h-200f)*1/3 + 100f,(w-200f)*9/10 + 100f,h/2-50f,paintInterieur)
        canvas.drawLine(w-100f,h/2-50f,(w-200f)*9/10 + 100f,h/2-50f,paintInterieur)

            //sortie pacman bas
        canvas.drawLine(w-100f,h/2+50f,(w-200f)*9/10 + 100f,h/2+50f,paintInterieur)
        canvas.drawLine((w-200f)*9/10 + 100f,h/2+50f,(w-200f)*9/10 + 100f,(h-200f)*2/3 + 100f,paintInterieur)
        canvas.drawLine((w-200f)*9/10 + 100f,(h-200f)*2/3 + 100f,w-100f,(h-200f)*2/3 + 100f,paintInterieur)
        /* Dessin mur bas*/
        canvas.drawLine(w - 100f, h - 100f, 100f, h - 100f, paintInterieur)

        /*Dessin mur gauche*/

        // haut
        canvas.drawLine(100f,100f,100f,(h-200f)*1/3 + 100f,paintInterieur)

        // bas
        canvas.drawLine(100f,(h-200f)*2/3 + 100f , 100f,h-100f,paintInterieur)

        // sortie pacman haut
        canvas.drawLine(100f,(h-200f)*1/3 + 100f,(w-200f)/10 + 100f,(h-200f)*1/3 + 100f,paintInterieur)
        canvas.drawLine((w-200f)/10 + 100f ,(h-200f)*1/3 + 100f,(w-200f)/10 + 100f, h/2-50f, paintInterieur )
        canvas.drawLine((w-200f)/10 + 100f,h/2-50f,100f,h/2-50f,paintInterieur)

        //sortie pacman bas

        canvas.drawLine(100f,h/2+50f,(w-200f)/10 + 100f,h/2+50f,paintInterieur)
        canvas.drawLine((w-200f)/10 + 100f,h/2+50f,(w-200f)/10 + 100f,(h-200f)*2/3 + 100f,paintInterieur)
        canvas.drawLine((w-200f)/10 + 100f,(h-200f)*2/3 + 100f,100f,(h-200f)*2/3 + 100f,paintInterieur)

        // Dessin mur pour que pacman passe de gauche à droite






        // Dessin spawn fantôme
        canvas.drawLine(w/2-100f,h/2-50f,w/2-100f/3,h/2-50f,paintInterieur)
        canvas.drawLine(w/2+100f/3,h/2-50f,w/2 + 100f,h/2 -50f,paintInterieur)

        canvas.drawLine(w/2+100f,h/2-50f, w/2+100f,h/2+50f,paintInterieur)
        canvas.drawLine(w/2-100f,h/2-50f, w/2-100f,h/2+50f,paintInterieur)

        canvas.drawLine(w/2-100f,h/2+50f,w/2+100f,h/2+50f,paintInterieur)



        // Déverrouiller le canvas pour terminer le dessin
        holder.unlockCanvasAndPost(canvas)
    }
}
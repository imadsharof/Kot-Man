package com.example.pac_man

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var surfaceView: SurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Masquer la barre d'état
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        // Créer une instance de SurfaceDeJeu
        surfaceView = SurfaceView(this)

        // Initialiser la SurfaceView
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                // Initialiser vos éléments graphiques ici, tels que les images, les tableaux de données, etc.

                // Créer une instance de la classe DessinMur pour dessiner les murs
                val mur = Mur(surfaceView)

                // Dessiner les murs
                mur.dessiner()
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                // La surface a été modifiée
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                // Nettoyer les ressources utilisées par les éléments graphiques
            }
        })

        // Ajouter la SurfaceView à votre mise en page XML
        setContentView(surfaceView)
    }
}
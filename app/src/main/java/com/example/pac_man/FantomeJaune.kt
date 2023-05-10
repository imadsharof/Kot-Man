package com.example.pac_man

import android.content.res.Resources

class FantomeJaune(
    resources: Resources,
    caseWidth: Float,
    caseHeight: Float,
    labyrinthe: Labyrinthe
) : Fantome(resources, caseWidth, caseHeight, R.drawable.fantomejaune,R.drawable.fantomegris,labyrinthe) {

    override fun spawnFantome() {
        // Initialise la position du fantôme vert dans le labyrinthe
        tileX = 11F
        tileY = 13F
    }
}
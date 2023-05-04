package com.example.pac_man

import android.content.res.Resources

class FantomeJaune(
    resources: Resources,
    caseWidth: Float,
    caseHeight: Float
) : Fantome(resources, caseWidth, caseHeight, R.drawable.fantomejaune) {

    override fun spawnFantome() {
        // Initialise la position du fantôme vert dans le labyrinthe
        tileX = 14F
        tileY = 13F
    }
}
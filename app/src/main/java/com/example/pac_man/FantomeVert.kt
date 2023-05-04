package com.example.pac_man

import android.content.res.Resources

class FantomeVert(
    resources: Resources,
    caseWidth: Float,
    caseHeight: Float
) : Fantome(resources, caseWidth, caseHeight, R.drawable.fantomevert) {

    override fun spawnFantome() {
        // Initialise la position du fantôme vert dans le labyrinthe
        tileX = 13F
        tileY = 13F
    }
}
package com.example.pac_man

import android.content.res.Resources

class FantomeVert(
    resources: Resources,
    caseWidth: Float,
    caseHeight: Float,
    labyrinthe: Labyrinthe
) : Fantome(resources, caseWidth, caseHeight, R.drawable.fantomevert,R.drawable.fantomegris,labyrinthe) {

    override fun spawnFantome() {
        // Initialise la position du fantôme vert dans le labyrinthe
        tileX = 12F
        tileY = 12F
    }
}
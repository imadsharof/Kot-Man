package com.example.pac_man

import android.content.res.Resources

class FantomeRouge(
    resources: Resources,
    caseWidth: Float,
    caseHeight: Float,
    labyrinthe: Labyrinthe
) : Fantome(resources, caseWidth, caseHeight, R.drawable.fantomerouge,R.drawable.fantomegris,labyrinthe) {

    override fun spawnFantome() {
        // Initialise la position du fantôme vert dans le labyrinthe
        tileX = 13F
        tileY = 13F
    }
}
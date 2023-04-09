package com.example.pac_man

import android.content.res.Resources

class FantomeJaune(
    resources: Resources,
    caseWidth: Int,
    caseHeight: Int
) : Fantome(resources, caseWidth, caseHeight, R.drawable.fantomejaune) {

    override fun spawnFantome() {
        // Initialise la position du fantôme vert dans le labyrinthe
        posX = 14 * caseWidth
        posY = 13 * caseHeight
    }
}
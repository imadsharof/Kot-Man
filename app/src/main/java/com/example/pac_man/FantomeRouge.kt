package com.example.pac_man

import android.content.res.Resources

class FantomeRouge(
    resources: Resources,
    caseWidth: Float,
    caseHeight: Float,
    labyrinthe: Labyrinthe
) : Fantome(resources, caseWidth, caseHeight, R.drawable.fantomerouge,R.drawable.fantomegris,labyrinthe) {

    // redéfinie pour initialiser la position du fantôme bleu dans le labyrinthe à (12,13)
    override fun spawnFantome() {
        tileX = 13F
        tileY = 13F
    }
}
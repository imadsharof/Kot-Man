package com.example.pac_man

import android.content.res.Resources
import android.graphics.Canvas

class FantomeBleu(
    resources: Resources,
    caseWidth: Float,
    caseHeight: Float,
    labyrinthe: Labyrinthe
) : Fantome(resources, caseWidth, caseHeight, R.drawable.fantomebleu,R.drawable.fantomegris,labyrinthe) {

    override fun spawnFantome() {
        // Initialise la position du fantôme vert dans le labyrinthe
        tileX = 12F
        tileY = 13F
    }
    }
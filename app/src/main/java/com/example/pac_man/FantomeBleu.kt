package com.example.pac_man

import android.content.res.Resources
import android.graphics.Bitmap

class FantomeBleu (
    resources: Resources,
    caseWidth: Float,
    caseHeight: Float
) : Fantome(resources, caseWidth, caseHeight, R.drawable.fantomebleu,R.drawable.fantomegris) {

    override fun spawnFantome() {
        // Initialise la position du fantôme vert dans le labyrinthe
        tileX = 12F
        tileY = 13F
    }
}
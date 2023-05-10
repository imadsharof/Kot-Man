package com.example.pac_man

import android.content.res.Resources
import android.graphics.Canvas

/* respecte le principe OCP (Open/Closed Principle) car elle est ouverte à l'extension
pour ajouter de nouveaux types de fantômes (elle hérite de la classe abstraite Fantome),
mais fermée à la modification car elle ne modifie pas le comportement existant de la classe parente.*/
class FantomeBleu(
    resources: Resources,
    caseWidth: Float,
    caseHeight: Float,
    labyrinthe: Labyrinthe
) : Fantome(resources, caseWidth, caseHeight, R.drawable.fantomebleu,R.drawable.fantomegris,labyrinthe) {

    // redéfinie pour initialiser la position du fantôme bleu dans le labyrinthe à (12,13)
    override fun spawnFantome() {
        tileX = 12F
        tileY = 13F
    }
    }
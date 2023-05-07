package com.example.pac_man

import android.content.res.Resources

class BonusCafe(
    resources: Resources,
    caseWidth: Float,
    caseHeight: Float
) : Bonus(resources, caseWidth, caseHeight, R.drawable.bonuscafe,2) {

    override fun spawnBonus() {
        if (!isVisible && !isCollected) {
            tileX = 12F
            tileY = 19F
            isVisible = true
            isCollected = false
        }
    }

    override fun update() {
        val elapsedTime = System.currentTimeMillis() - startTime
        val elapsedSeconds = elapsedTime / 1000

        // Le bonus apparait de 00:30 sec à 00:40 puis disparait et revient chaque apres timeBonus minute
        if (elapsedSeconds % timeBonus >= 10 && elapsedSeconds % timeBonus < 20) {
            if (!isCollected) {
                spawnBonus()
            }

            // Le bonus disparait de 00:40 à jusque (1:30 + timeBonus ) et revient apres chaque timeBonus minute
        } else if (elapsedSeconds % timeBonus >= 20 ) {
            isCollected = false
            isVisible = false

            // Avant les 30 sec le bonus n'apparait pas
        } else {
            hideBonus()
        }

    }
}


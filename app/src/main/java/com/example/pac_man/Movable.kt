package com.example.pac_man

interface Movable {
    fun moveUp()
    fun moveDown()
    fun moveLeft()
    fun moveRight()
}
/*Cela permet de travailler avec des objets PacMan en utilisant uniquement l'interface Movable,
 ce qui facilite le respect du principe d'inversion des dépendances (DIP) dans le cadre des principes SOLID*/
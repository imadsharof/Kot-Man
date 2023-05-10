package com.example.pac_man

interface Observable {
    val observers: ArrayList<Observer>
    fun hasUpdated() {
        for (observer in observers){
            observer.update(false)
        }
    }
}
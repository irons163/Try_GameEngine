package com.example.try_gameengine.observer

interface Subject {
    fun registerObserver(o: Observer?)
    fun removeObserver(o: Observer?)
    fun notifyObservers()
}

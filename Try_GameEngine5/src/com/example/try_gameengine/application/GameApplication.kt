package com.example.try_gameengine.application

import android.app.Application

abstract class GameApplication : Application() {
    override fun onCreate() {
        // TODO Auto-generated method stub
        super.onCreate()
        startGame()
    }

    abstract fun startGame()

    abstract fun stopGame()

    abstract fun resumeGame()

    abstract fun finishGame()
}

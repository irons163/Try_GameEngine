package com.example.try_gameengine.avg

import java.util.concurrent.TimeUnit

object AVGUtils {
    fun pause(timeMillis: Long) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeMillis)
        } catch (e: InterruptedException) {
            throw RuntimeException("Interrupted in pause !")
        }
    }
}

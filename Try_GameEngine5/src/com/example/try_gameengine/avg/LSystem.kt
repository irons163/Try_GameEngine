package com.example.try_gameengine.avg

import android.app.Activity
import android.content.Context
import java.util.Random

object LSystem {
    var DEFAULT_MAX_CACHE_SIZE: Int = 10
    var w: Int = 800
    var h: Int = 1200
    var encoding: String = "UTF-8"
    var random: Random = Random()

    //	public static int getRandom() {
    var systemHandler: SystemHandler? = null
    const val FONT_NAME: String = "Monospaced"
    const val DEFAULT_MAX_FPS: Int = 50

    fun gc() {
        System.gc()
    }

    fun getRandom(i: Int, j: Int): Int {
        val random = Random()
        return i + random.nextInt((j - i) + 1)
    }

    //		Random random = new Random();
    //		return i + random.nextInt((j - i) + 1);
    //	}
}

class SystemHandler {
    var activity: Activity? = null

    val context: Context?
        get() = activity

    private var asm: AssetsSoundManager? = null

    val assetsSound: AssetsSoundManager?
        /**
         * 
         * @return
         // */
        get() {
            if (this.asm == null) {
                this.asm = AssetsSoundManager.Companion.getInstance(activity)
            }
            return asm
        }
}

package com.example.try_gameengine.avg

import android.content.Context

/**
 * 
 * Copyright 2008 - 2010
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loonframework
 * @author chenpeng
 * @email ceponline@yahoo.com.cn
 * @version 0.1.0
 // */
class AssetsSoundManager private constructor(private val context: Context?) {
    private val sounds: ArrayMap? = ArrayMap(MAX_CLIPS)

    private var clipCount = 0

    private var paused = false

    private var asound: AssetsSound? = null

    @Synchronized
    fun playSound(name: String?, vol: Int) {
        if (paused) {
            return
        }
        if (sounds!!.containsKey(name)) {
            (sounds.get(name) as AssetsSound).play()
        } else {
            if (clipCount > MAX_CLIPS) {
                val idx = sounds.size - 1
                val k = sounds.keys.toTypedArray()[idx] as String?
                var clip = sounds.remove(k) as AssetsSound?
                clip!!.release()
                clip = null
                clipCount--
            }
            asound = AssetsSound(context!!)
            asound!!.setDataSource(name!!)
            asound!!.play(vol)
            sounds.put(name, asound)
            clipCount++
        }
    }

    @Synchronized
    fun stopSound(index: Int) {
        val sound = sounds!!.get(index) as AssetsSound?
        if (sound != null) {
            sound.stopPlayer()
            sound.release()
        }
    }

    @Synchronized
    fun playSound(name: String?, loop: Boolean) {
        if (paused) {
            return
        }
        if (sounds!!.containsKey(name)) {
            (sounds.get(name) as AssetsSound).play()
        } else {
            if (clipCount > MAX_CLIPS) {
                val idx = sounds.size - 1
                val k = sounds.keys.toTypedArray()[idx] as String?
                var clip = sounds.remove(k) as AssetsSound?
                clip!!.release()
                clip = null
                clipCount--
            }
            asound = AssetsSound(context!!)
            asound!!.setDataSource(name!!)
            if (loop) {
                asound!!.loop()
            } else {
                asound!!.play()
            }
            sounds.put(name, asound)
            clipCount++
        }
    }

    @Synchronized
    fun stopSoundAll() {
        if (sounds != null) {
            val it = sounds.iterator()
            while (it.hasNext()) {
                val sound = it.next() as MutableMap.MutableEntry<*, *>?
                if (sound != null) {
                    val `as` = sound.value as AssetsSound?
                    if (`as` != null) {
                        `as`.stop()
                    }
                }
            }
        }
    }

    @Synchronized
    fun resetSound() {
        if (asound != null) {
            asound!!.reset()
        }
    }

    @Synchronized
    fun stopSound() {
        if (asound != null) {
            asound!!.stop()
            asound!!.release()
        }
    }

    @Synchronized
    fun stopPlayer() {
        if (asound != null) {
            asound!!.stopPlayer()
            asound!!.release()
        }
    }

    @Synchronized
    fun setSoundVolume(vol: Int) {
        if (asound != null) {
            asound!!.setVolume(vol)
        }
    }

    @Synchronized
    fun pause(pause: Boolean) {
        paused = pause
    }

    companion object {
        const val MAX_CLIPS: Int = 50

        private var am: AssetsSoundManager? = null

        fun getInstance(ctx: Context?): AssetsSoundManager {
            if (am == null) {
                return (AssetsSoundManager(ctx).also { am = it })
            }
            return am!!
        }
    }
}

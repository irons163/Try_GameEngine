package com.example.try_gameengine.avg

import android.graphics.Bitmap
import java.util.Locale

/**
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
 * @emaileponline@yahoo.com.cn
 * @version 0.1
 // */
class CG {
    var backgroundCG: Bitmap? = null

    val charas: ArrayMap

    init {
        charas = ArrayMap(100)
    }

    fun noneBackgroundCG() {
        this.backgroundCG = null
    }

    fun setBackgroundCG(resName: String?) {
        this.backgroundCG = GraphicsUtils.loadImage(resName)
    }

    fun addChara(file: String, role: Chara?) {
        charas.put(file.replace(" ".toRegex(), "").lowercase(Locale.getDefault()), role)
    }

    fun addImage(name: String, x: Int, y: Int, w: Int) {
        val keyName = name.replace(" ".toRegex(), "").lowercase(Locale.getDefault())
        val chara = charas.get(keyName) as Chara?
        if (chara == null) {
            charas.put(keyName, Chara(name, x, y, w))
        } else {
            chara.setX(x)
            chara.y = y
        }
    }

    fun removeImage(file: String): Chara? {
        return charas.remove(
            file.replace(" ".toRegex(), "").lowercase(Locale.getDefault())
        ) as Chara?
    }

    fun dispose() {
        for (i in 0..<charas.size) {
            var ch = charas.get(i) as Chara?
            ch!!.dispose()
            ch = null
        }
        charas.clear()
    }

    fun clear() {
        charas.clear()
    }
}

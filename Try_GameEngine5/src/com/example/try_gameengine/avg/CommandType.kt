package com.example.try_gameengine.avg

/**
 * Copyright 2008 - 2009
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
interface CommandType {
    companion object {
        const val L_WAIT: String = "wait"

        const val L_MES: String = "mes"

        const val L_SELLEN: String = "selleft"

        const val L_SELTOP: String = "seltop"

        const val L_MESLEN: String = "meslen"

        const val L_MESTOP: String = "mestop"

        const val L_MESLEFT: String = "mesleft"

        const val L_MESCOLOR: String = "mescolor"

        const val L_MESSTOP: String = "messtop"

        const val L_SELECT: String = "select"

        const val L_SELECTS: String = "selects"

        const val L_SHAKE: String = "shake"

        const val L_CGWAIT: String = "cgwait"

        const val L_SLEEP: String = "sleep"

        const val L_FLASH: String = "flash"

        const val L_GB: String = "gb"

        const val L_CG: String = "cg"

        const val L_PLAY: String = "play"

        const val L_PLAYLOOP: String = "playloop"

        const val L_PLAYSTOP: String = "playstop"

        const val L_FADEOUT: String = "fadeout"

        const val L_FADEIN: String = "fadein"

        const val L_DEL: String = "del"

        const val L_SNOW: String = "snow"

        const val L_RAIN: String = "rain"

        const val L_PETAL: String = "petal"

        const val L_SNOWSTOP: String = "snowstop"

        const val L_RAINSTOP: String = "rainstop"

        const val L_PETALSTOP: String = "petalstop"

        const val L_TO: String = "to"

        const val L_EXIT: String = "exit"
    }
}

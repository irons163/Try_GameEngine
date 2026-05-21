package com.example.try_gameengine.map

import android.graphics.Bitmap
import com.example.try_gameengine.avg.Resources
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

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
 * @email ceponline@yahoo.com.cn
 * @version 0.1
 // */
class TileMapConfig {
    var backMap: Array<IntArray?>? = null

    companion object {
        @Throws(IOException::class)
        fun loadList(fileName: String?): MutableList<IntArray?> {
            val `in`: InputStream? = Resources.Companion.openResource(fileName)
            var reader: BufferedReader? = BufferedReader(InputStreamReader(`in`))
            val records: MutableList<IntArray?> = ArrayList<IntArray?>(10)
            var result: String? = null
            try {
                while ((reader!!.readLine().also { result = it }) != null) {
                    if ("" != result) {
                        val stringArray: Array<String?> =
                            result!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                                .toTypedArray()
                        val size = stringArray.size
                        val intArray = IntArray(size)
                        for (i in 0..<size) {
                            intArray[i] = stringArray[i]!!.toInt()
                        }
                        records.add(intArray)
                    }
                }
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                        reader = null
                    } catch (ex: IOException) {
                    }
                }
            }
            return records
        }

        fun reversalXandY(array: Array<Array<Bitmap?>?>): Array<Array<Bitmap?>?> {
            val col = array[0]!!.size
            val row = array.size
            val result = Array<Array<Bitmap?>?>(col) { arrayOfNulls<Bitmap>(row) }
            for (y in 0..<col) {
                for (x in 0..<row) {
                    result[x]!![y] = array[y]!![x]
                }
            }
            return result
        }

        fun reversalXandY(array: Array<IntArray?>): Array<IntArray?> {
            val col = array[0]!!.size
            val row = array.size
            val result = Array<IntArray?>(col) { IntArray(row) }
            for (y in 0..<col) {
                for (x in 0..<row) {
                    result[x]!![y] = array[y]!![x]
                }
            }
            return result
        }

        @Throws(IOException::class)
        fun loadAthwartArray(fileName: String?): Array<IntArray?> {
            val list: MutableList<*> = loadList(fileName)
            val col = list.size
            val result = arrayOfNulls<IntArray>(col)
            for (i in 0..<col) {
                result[i] = list.get(i) as IntArray?
            }
            return result
        }

        @Throws(IOException::class)
        fun loadJustArray(fileName: String?): Array<IntArray?> {
            val list: MutableList<*> = loadList(fileName)
            val col = list.size
            val mapArray = arrayOfNulls<IntArray>(col)
            for (i in 0..<col) {
                mapArray[i] = list.get(i) as IntArray?
            }
            val row = ((mapArray[if (col > 0) col - 1 else 0] as IntArray).size)
            val result = Array<IntArray?>(row) { IntArray(col) }
            for (y in 0..<col) {
                for (x in 0..<row) {
                    result[x]!![y] = mapArray[y]!![x]
                }
            }
            return result
        }
    }
}

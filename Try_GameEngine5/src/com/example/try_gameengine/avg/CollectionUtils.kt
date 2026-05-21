package com.example.try_gameengine.avg

import java.lang.reflect.Array as ReflectArray
import kotlin.math.min

/**
 * 
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
 * @email：ceponline@yahoo.com.cn
 * @version 0.1.1
 // */
object CollectionUtils {
    const val INITIAL_CAPACITY: Int = 10

    /**
     * 判定指定物件是否存在於指定物件陣列中
     * 
     * @param array
     * @param obj
     * @return
     // */
    fun indexOf(array: Array<Any?>, obj: Any?): Int {
        for (i in array.indices) {
            if (obj === array[i]) {
                return i
            }
        }
        throw NoSuchElementException("" + obj)
    }

    /**
     * 將byte[]轉化為char[]
     * 
     * @param bytes
     * @return
     // */
    fun byteToChar(bytes: ByteArray): CharArray {
        val tempArray = CharArray(bytes.size)
        for (i in bytes.indices) {
            tempArray[i] = Char(bytes[i].toUShort())
        }
        return tempArray
    }

    /**
     * 將char[]轉化為byte[]
     * 
     * @param chars
     * @return
     // */
    fun charToByte(chars: CharArray): ByteArray {
        val tempArray = ByteArray(chars.size)
        for (i in chars.indices) {
            tempArray[i] = chars[i].code.toByte()
        }
        return tempArray
    }

    /**
     * 將String轉化為byte[]
     * 
     * @param string
     * @return
     // */
    fun stringToByte(string: String): ByteArray {
        val chars = string.toCharArray()
        val tempArray = ByteArray(chars.size)
        for (i in chars.indices) {
            tempArray[i] = chars[i].code.toByte()
        }
        return tempArray
    }

    /**
     * 將double[]轉換為int[]
     * 
     * @param doubles
     * @return
     // */
    fun doubleToInt(doubles: DoubleArray): IntArray {
        val size = doubles.size
        val valorInt = IntArray(size)
        for (i in 0..<size) {
            valorInt[i] = doubles[i].toInt()
        }
        return valorInt
    }

    /**
     * 將float轉換為int[]
     * 
     * @param ints
     * @return
     // */
    fun floatToInt(ints: FloatArray): IntArray {
        val size = ints.size
        val valorInt = IntArray(size)
        for (i in 0..<size) {
            valorInt[i] = ints[i].toInt()
        }
        return valorInt
    }

    /**
     * 彙聚多個String[]到一個中
     * 
     * @param as
     * @return
     // */
    fun compactStrings(`as`: Array<String?>): Array<String?> {
        val as1: Array<String?>? = arrayOfNulls<String>(`as`.size)
        var i = 0
        for (j in `as`.indices) {
            i += `as`[j]!!.length
        }
        val ac: CharArray? = CharArray(i)
        i = 0
        for (k in `as`.indices) {
            `as`[k]!!.toCharArray(ac!!, i, 0, `as`[k]!!.length)
            i += `as`[k]!!.length
        }
        val s = kotlin.text.String(ac!!)
        i = 0
        for (l in `as`.indices) {
            as1!![l] = s.substring(i, `as`[l]!!.length.let { i += it; i })
        }
        return as1!!
    }

    /**
     * 擴充指定陣列
     * 
     * @param obj
     * @param i
     * @param flag
     * @return
     // */
    fun expand(obj: Any, i: Int, flag: Boolean): Any {
        val j = ReflectArray.getLength(obj)
        val obj1 = ReflectArray.newInstance(
            obj.javaClass.getComponentType(), j
                    + i
        )
        System.arraycopy(obj, 0, obj1, if (flag) 0 else i, j)
        return obj1
    }

    /**
     * 擴充指定陣列
     * 
     * @param obj
     * @param size
     * @return
     // */
    fun expand(obj: Any, size: Int): Any {
        return expand(obj, size, true)
    }

    /**
     * 擴充指定陣列
     * 
     * @param obj
     * @param size
     * @param flag
     * @param class1
     * @return
     // */
    fun expand(obj: Any?, size: Int, flag: Boolean, class1: Class<*>): Any {
        if (obj == null) {
            return ReflectArray.newInstance(class1, 1)
        } else {
            return expand(obj, size, flag)
        }
    }

    /**
     * 剪切出指定長度的陣列
     * 
     * @param obj
     * @param size
     * @return
     // */
    fun cut(obj: Any, size: Int): Any {
        var j: Int
        if ((ReflectArray.getLength(obj).also { j = it }) == 1) {
            return ReflectArray.newInstance(obj.javaClass.getComponentType(), 0)
        }
        val k: Int
        if (((j - size - 1).also { k = it }) > 0) {
            System.arraycopy(obj, size + 1, obj, size, k)
        }
        j--
        val obj1 = ReflectArray.newInstance(obj.javaClass.getComponentType(), j)
        System.arraycopy(obj, 0, obj1, 0, j)
        return obj1
    }

    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @param newSize
     * @return
     // */
    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @return
     // */
    @JvmOverloads
    fun copyOf(obj: kotlin.Array<Any?>, newSize: Int = obj.size): kotlin.Array<Any?> {
        return copyOf(obj, newSize, ((obj) as Any).javaClass)
    }

    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @param newSize
     * @param newType
     * @return
     // */
    private fun copyOf(
        obj: kotlin.Array<Any?>?,
        newSize: Int,
        newType: Class<out Any?>
    ): kotlin.Array<Any?> {
        val copy = if (newType as Any? === kotlin.Array<Any>::class.java as Any)
            arrayOfNulls<Any>(newSize)
        else
            ReflectArray.newInstance(
                newType.getComponentType(),
                newSize
            ) as kotlin.Array<Any?>
        return copy
    }

    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @param newSize
     * @return
     // */
    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @return
     // */
    @JvmOverloads
    fun copyOf(obj: IntArray, newSize: Int = obj.size): IntArray {
        val tempArr: IntArray? = IntArray(newSize)
        System.arraycopy(obj, 0, tempArr, 0, min(obj.size, newSize))
        return tempArr!!
    }

    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @param newSize
     * @return
     // */
    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @return
     // */
    @JvmOverloads
    fun copyOf(obj: DoubleArray, newSize: Int = obj.size): DoubleArray {
        val tempArr: DoubleArray? = DoubleArray(newSize)
        System.arraycopy(obj, 0, tempArr, 0, min(obj.size, newSize))
        return tempArr!!
    }

    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @param newSize
     * @return
     // */
    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @return
     // */
    @JvmOverloads
    fun copyOf(obj: FloatArray, newSize: Int = obj.size): FloatArray {
        val tempArr: FloatArray? = FloatArray(newSize)
        System.arraycopy(obj, 0, tempArr, 0, min(obj.size, newSize))
        return tempArr!!
    }

    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @param newSize
     * @return
     // */
    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @return
     // */
    @JvmOverloads
    fun copyOf(obj: ByteArray, newSize: Int = obj.size): ByteArray {
        val tempArr: ByteArray? = ByteArray(newSize)
        System.arraycopy(obj, 0, tempArr, 0, min(obj.size, newSize))
        return tempArr!!
    }

    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @param newSize
     * @return
     // */
    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @return
     // */
    @JvmOverloads
    fun copyOf(obj: CharArray, newSize: Int = obj.size): CharArray {
        val tempArr: CharArray? = CharArray(newSize)
        System.arraycopy(obj, 0, tempArr, 0, min(obj.size, newSize))
        return tempArr!!
    }

    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @param newSize
     * @return
     // */
    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @return
     // */
    @JvmOverloads
    fun copyOf(obj: LongArray, newSize: Int = obj.size): LongArray {
        val tempArr: LongArray? = LongArray(newSize)
        System.arraycopy(obj, 0, tempArr, 0, min(obj.size, newSize))
        return tempArr!!
    }

    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @param newSize
     * @return
     // */
    /**
     * copy指定長度的陣列資料
     * 
     * @param obj
     * @return
     // */
    @JvmOverloads
    fun copyOf(obj: BooleanArray, newSize: Int = obj.size): BooleanArray {
        val tempArr: BooleanArray? = BooleanArray(newSize)
        System.arraycopy(obj, 0, tempArr, 0, min(obj.size, newSize))
        return tempArr!!
    }
}

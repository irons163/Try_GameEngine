package com.example.try_gameengine.avg

import java.math.BigDecimal
import java.util.Random
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

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
 * @email：ceponline@yahoo.com.cn
 * @version 0.1
 // */
object NumberUtils {
    private const val DEF_DIV_SCALE = 10

    /**
     * 獲得一個隨機的unsigned int
     * 
     * @param maxInt
     * @param doNotInclude1
     * @param doNotInclude2
     * @return
     // */
    fun getRandomUnsignedInt(
        maxInt: Int, doNotInclude1: Int,
        doNotInclude2: Int
    ): Int {
        var doNotInclude1 = doNotInclude1
        var doNotInclude2 = doNotInclude2
        var n = 2
        if (doNotInclude1 == doNotInclude2) {
            doNotInclude2 = maxInt + 1
        }
        if (doNotInclude1 > doNotInclude2) {
            n = doNotInclude2
            doNotInclude2 = doNotInclude1
            doNotInclude1 = n
            n = 2
        }
        if (doNotInclude1 < 0) {
            doNotInclude1 = maxInt + 1
        }
        if (doNotInclude2 < 0) {
            doNotInclude2 = maxInt + 1
        }
        if (doNotInclude1 > maxInt) {
            n--
        }
        if (doNotInclude2 > maxInt) {
            n--
        }
        var `val` = floor(
            Math.random()
                    * (maxInt.toDouble() - n.toDouble())
        ).toInt()
        if (`val` >= doNotInclude1) {
            `val`++
        }
        if (`val` >= doNotInclude2) {
            `val`++
        }
        return `val`
    }

    /**
     * 獲得一個隨機的unsigned int
     * 
     * @param maxInt
     * @param doNotInclude
     * @return
     // */
    fun getRandomUnsignedInt(maxInt: Int, doNotInclude: Int): Int {
        var `val` = 0
        if (doNotInclude > -1 && doNotInclude <= maxInt) {
            `val` = floor(Math.random() * (maxInt.toDouble() - 1.0)).toInt()
            if (`val` >= doNotInclude) {
                `val`++
            }
        } else {
            `val` = floor(Math.random() * maxInt.toDouble()).toInt()
        }
        return `val`
    }

    /**
     * 獲得一個隨機的unsigned int
     * 
     * @param maxInt
     * @return
     // */
    fun getRandomUnsignedInt(maxInt: Int): Int {
        return getRandomUnsignedInt(maxInt, -1)
    }

    /**
     * 返回一組亂數
     * 
     * @param num1
     * @param num2
     * @return
     // */
    fun getRandomInt(num1: Int, num2: Int): Int {
        var result = 0
        if (num2 > -1 && num2 <= num1) {
            result = floor(Math.random() * (num1.toDouble() - 1.0)).toInt()
            if (result >= num2) {
                result++
            }
        } else {
            result = floor(Math.random() * num1.toDouble()).toInt()
        }
        return result
    }

    /**
     * 取中值
     * 
     * @param i
     * @param min
     * @param max
     * @return
     // */
    fun mid(i: Int, min: Int, max: Int): Int {
        return max(i, min(min, max))
    }

    private val zeros = arrayOf<String?>(
        "", "0", "00", "000", "0000",
        "00000", "000000", "0000000", "00000000", "000000000", "0000000000"
    )

    /**
     * 為指定數值補足位數
     * 
     * @param number
     * @param numDigits
     * @return
     // */
    fun addZeros(number: Long, numDigits: Int): String {
        return addZeros(number.toString(), numDigits)
    }

    /**
     * 為指定數值補足位數
     * 
     * @param number
     * @param numDigits
     * @return
     // */
    fun addZeros(number: String, numDigits: Int): String {
        var number = number
        val length = numDigits - number.length
        if (length != 0) {
            number = zeros[length] + number
        }
        return number
    }

    /**
     * 判斷是否為數字
     * 
     * @param param
     * @return
     // */
    fun isNan(param: String?): Boolean {
        var param = param
        var result = false
        if (param == null || "" == param) {
            return result
        }
        param = param.replace('d', '_').replace('f', '_')
        try {
            val test: Double = param.toDouble()
            test.toInt()
            result = true
        } catch (ex: NumberFormatException) {
            return result
        }
        return result
    }

    /**
     * 檢查一個數字是否為空
     * 
     * @param val
     * @return
     // */
    fun isEmpty(`val`: Int): Boolean {
        return if (`val` == Int.Companion.MIN_VALUE) true else 0 == `val`
    }

    /**
     * 檢查一個字串數位是否為空
     * 
     * @param val
     * @return
     // */
    fun isEmpty(`val`: String?): Boolean {
        return ((`val` == null) or ("" == `val`) or (`val` == Int.Companion.MAX_VALUE.toString()))
    }

    /**
     * 單純計算兩個數值的百分比
     * 
     * @param divisor
     * @param dividend
     * @return
     // */
    fun toPercent(divisor: Long, dividend: Long): Double {
        if (divisor == 0L || dividend == 0L) {
            return 0.0
        }
        val cd = divisor * 1.0
        val pd = dividend * 1.0

        return (Math.round(cd / pd * 10000) * 1.0) / 100
    }

    /**
     * 獲得一組指定長度的亂數
     * 
     * @param size
     * @return
     // */
    fun toRandom(size: Int): Int {
        val rad = Random()
        rad.setSeed(System.currentTimeMillis())
        return abs(rad.nextInt()) % size
    }


    /**
     * 獲得100%進制剩餘數值百分比。
     * 
     * @param maxValue
     * @param minusValue
     * @return
     // */
    fun minusPercent(maxValue: Float, minusValue: Float): Float {
        return 100 - ((minusValue / maxValue) * 100)
    }

    /**
     * 獲得100%進制數值百分比。
     * 
     * @param maxValue
     * @param minusValue
     * @return
     // */
    fun percent(maxValue: Float, minValue: Float): Float {
        return (minValue / maxValue) * 100
    }

    /**
     * 將value轉化成中文數位的大小寫
     * 
     * @param value
     * @param type
     * 1:大寫中文 2：小寫中文
     * 
     * @return
     // */
    fun toConvertCnNumber(value: Long, type: Int): String {
        var chNumber = arrayOf<String?>("零", "壹", "貳", "三", "肆", "伍", "陸", "柒", "捌", "玖")
        val digit = arrayOf<String>("", "拾", "佰", "仟", "萬", "十", "百", "仟")
        when (type) {
            1 -> {
                val capsCNumber = arrayOf<String?>(
                    "零", "壹", "貳", "三", "肆", "伍", "陸", "柒",
                    "捌", "玖"
                )
                chNumber = capsCNumber
                val minCNumber = arrayOf<String?>(
                    "零", "一", "二", "三", "四", "五", "六", "七",
                    "八", "九"
                )
                chNumber = minCNumber
            }

            2 -> {
                val minCNumber = arrayOf<String?>(
                    "零", "一", "二", "三", "四", "五", "六", "七",
                    "八", "九"
                )
                chNumber = minCNumber
            }
        }
        var retStr = ""

        val inputStr = value.toString()
        for (i in inputStr.length downTo 1) {
            val ch = inputStr.get(i - 1)
            if (ch != '0') {
                retStr = (chNumber[ch.code - '0'.code] + digit[inputStr.length - i]
                        + retStr)
            } else {
                if (inputStr.length - i == 4) retStr = "零萬" + retStr
                else retStr = "零" + retStr
            }
        }

        var pos = retStr.indexOf("零零")
        while (pos >= 0) {
            retStr = retStr.replace("零零".toRegex(), "零")
            pos = retStr.indexOf("零零")
        }

        retStr = retStr.replace("零萬".toRegex(), "萬")

        return retStr
    }

    /**
     * 提供精確的加法運算。
     * 
     * @param v1
     * 被加數
     * @param v2
     * 加數
     * @return 兩個參數的和
     // */
    fun add(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(v1.toString())
        val b2 = BigDecimal(v2.toString())
        return b1.add(b2).toDouble()
    }

    /**
     * 提供精確的減法運算。
     * 
     * @param v1
     * 被減數
     * @param v2
     * 減數
     * @return 兩個參數的差
     // */
    fun sub(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(v1.toString())
        val b2 = BigDecimal(v2.toString())
        return b1.subtract(b2).toDouble()
    }

    /**
     * 提供精確的乘法運算。
     * 
     * @param v1
     * 被乘數
     * @param v2
     * 乘數
     * @return 兩個參數的積
     // */
    fun mul(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(v1.toString())
        val b2 = BigDecimal(v2.toString())
        return b1.multiply(b2).toDouble()
    }

    /**
     * 提供（相對）精確的除法運算。當發生除不盡的情況時，由scale參數指 定精度，以後的數字四捨五入。
     * 
     * @param v1
     * 被除數
     * @param v2
     * 除數
     * @param scale
     * 表示表示需要精確到小數點以後幾位。
     * @return 兩個參數的商
     // */
    /**
     * 提供（相對）精確的除法運算，當發生除不盡的情況時，精確到 小數點以後10位元，以後的數字四捨五入。
     * 
     * @param v1
     * 被除數
     * @param v2
     * 除數
     * @return 兩個參數的商
     // */
    @JvmOverloads
    fun div(v1: Double, v2: Double, scale: Int = DEF_DIV_SCALE): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }

        val b1 = BigDecimal(v1.toString())
        val b2 = BigDecimal(v2.toString())
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     * 提供精確的小數位四捨五入處理。
     * 
     * @param v
     * 需要四捨五入的數位
     * @param scale
     * 小數點後保留幾位
     * @return 四捨五入後的結果
     // */
    fun round(v: Double, scale: Int): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }

        val b = BigDecimal(v.toString())
        val one = BigDecimal("1")
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }
}
